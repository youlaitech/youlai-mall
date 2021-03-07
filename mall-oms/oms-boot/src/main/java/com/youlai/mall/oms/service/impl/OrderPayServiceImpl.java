package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.oms.common.Query;
import com.youlai.common.mybatis.utils.PageUtils;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.mall.oms.common.EnumUtils;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.RequestUtils;
import com.youlai.mall.oms.dao.OrderPayDao;
import com.youlai.mall.oms.enums.OrderPayTypeEnum;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.oms.pojo.entity.OrderEntity;
import com.youlai.mall.oms.pojo.entity.OrderPayEntity;
import com.youlai.mall.oms.pojo.vo.PayInfoVO;
import com.youlai.mall.oms.service.OrderLogsService;
import com.youlai.mall.oms.service.OrderPayService;
import com.youlai.mall.oms.service.OrderService;
import com.youlai.mall.ums.api.MemberFeignService;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Slf4j
@AllArgsConstructor
@Service("orderPayService")
public class OrderPayServiceImpl extends ServiceImpl<OrderPayDao, OrderPayEntity> implements OrderPayService {

    private OrderService orderService;

    private OrderLogsService orderLogsService;

    private MemberFeignService memberFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderPayEntity> page = this.page(
                new Query<OrderPayEntity>().getPage(params),
                new QueryWrapper<OrderPayEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PayInfoVO info(String orderId) {
        Long userId = RequestUtils.getUserId();
        PayInfoVO payInfoVO = new PayInfoVO();
        // 1、获取订单应支付金额
        OrderEntity orderEntity = orderService.getByOrderId(orderId);
        payInfoVO.setPayPrice(orderEntity.getPayAmount());

        // 2、获取会员余额
        try {
            Result<MemberDTO> memberInfo = memberFeignService.getUserById(RequestUtils.getUserId());
            if (memberInfo != null && memberInfo.getCode().equals(ResultCode.SUCCESS.getCode())) {
                MemberDTO data = memberInfo.getData();
                if (data != null) {
                    payInfoVO.setBalance(data.getBalance());
                } else {
                    log.error("获取会员信息失败,userId={}", userId);
                }
            }
        } catch (Exception e) {
            log.error("获取会员余额失败,userId={}", userId, e);
        }

        return payInfoVO;
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void balancePay(String orderId) {
        // 1、查询订单详情，判断订单状态是否是待支付状态
        log.info("订单进入支付流程，orderId：{}", orderId);
        OrderEntity order = orderService.getByOrderId(orderId);
        OrderStatusEnum orderStatusEnum = EnumUtils.getByCode(order.getStatus(), OrderStatusEnum.class);
        if (orderStatusEnum != OrderStatusEnum.NEED_PAY) {
            log.error("订单状态异常无法支付，orderStatus={}", orderStatusEnum.desc);
            throw new BizException("订单" + orderStatusEnum.desc);
        }

        // 2、查询用户信息，判断用户余额是否足够
        Long userId = RequestUtils.getUserId();
        Result<MemberDTO> memberInfo = memberFeignService.getUserById(userId);
        MemberDTO memberInfoData = memberInfo.getData();
        if (memberInfo == null || !memberInfo.getCode().equals(ResultCode.SUCCESS.getCode()) || memberInfoData == null) {
            log.error("会员信息异常，无法支付");
            throw new BizException("会员信息异常，无法支付");
        }
        if (memberInfoData.getBalance() < order.getPayAmount()) {
            log.error("会员余额不足，无法支付，请先充值");
            throw new BizException("会员余额不足，无法支付，请先充值");
        }

        // 3、更新用户余额
        memberFeignService.updateBalance(userId, order.getPayAmount());

        // 4、更新订单状态、添加订单支付记录、添加订单操作记录
        order.setStatus(OrderStatusEnum.IS_PAY.code);
        order.setPayTime(new Date());
        order.setPayType(OrderPayTypeEnum.BALANCE.code);
        orderService.updateById(order);
        this.save(createOrderPay(order, OrderPayTypeEnum.BALANCE.code));
        orderLogsService.addOrderLogs(order.getId(), OrderStatusEnum.IS_PAY.code, userId.toString(), "支付订单");
    }

    private OrderPayEntity createOrderPay(OrderEntity order, Integer payType) {
        OrderPayEntity payEntity = new OrderPayEntity();
        payEntity.setOrderId(order.getId());
        payEntity.setPayAmount(order.getPayAmount());
        payEntity.setPayTime(new Date());
        payEntity.setPayType(payType);
        return payEntity;
    }

}
