package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.RequestUtils;
import com.youlai.mall.oms.dao.OrderPayDao;
import com.youlai.mall.oms.enums.PayTypeEnum;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.oms.pojo.domain.OmsOrder;
import com.youlai.mall.oms.pojo.domain.OmsOrderPay;
import com.youlai.mall.oms.pojo.vo.PayInfoVO;
import com.youlai.mall.oms.service.IOrderLogService;
import com.youlai.mall.oms.service.IOrderPayService;
import com.youlai.mall.oms.service.IOrderService;
import com.youlai.mall.ums.api.app.MemberFeignService;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;


@Slf4j
@Service
@AllArgsConstructor
public class OrderPayServiceImpl extends ServiceImpl<OrderPayDao, OmsOrderPay> implements IOrderPayService {

    private IOrderService orderService;

    private IOrderLogService orderLogService;

    private MemberFeignService memberFeignService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean payWithBalance(Long orderId) {
        // 1、查询订单详情，判断订单状态是否是待支付状态
        log.info("订单进入支付流程，orderId：{}", orderId);
        OmsOrder order = orderService.getByOrderId(orderId);
        if (!OrderStatusEnum.NEED_PAY.getCode().equals(order.getStatus())) {
            throw new BizException("订单" + OrderStatusEnum.getValue(order.getStatus()).getText());
        }

        // 2、远程调用查询会员余额
        Long userId = RequestUtils.getUserId();
        Long balance = memberFeignService.getUserById(userId).getData().getBalance();

        if(Long.compare(balance,order.getPayAmount())==-1){
            throw new BizException("会员余额不足，无法支付，请先充值");
        }

        // 3、更新用户余额
        memberFeignService.updateBalance(userId, order.getPayAmount());

        // 4、更新订单状态、添加订单支付记录、添加订单操作记录
        order.setStatus(OrderStatusEnum.IS_PAY.getCode());
        order.setPayTime(new Date());
        order.setPayType(PayTypeEnum.BALANCE.getCode());
        orderService.updateById(order);
        this.save(createOrderPay(order, PayTypeEnum.BALANCE.getCode()));
        orderLogService.addOrderLogs(order.getId(), OrderStatusEnum.IS_PAY.getCode(), userId.toString(), "支付订单");

        return false;
    }

    @Override
    public PayInfoVO getByOrderId(Long orderId) {
        Long userId = RequestUtils.getUserId();
        PayInfoVO payInfoVO = new PayInfoVO();
        // 1、获取订单应支付金额
        OmsOrder omsOrder = orderService.getByOrderId(orderId);
        payInfoVO.setPayPrice(omsOrder.getPayAmount());

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

    private OmsOrderPay createOrderPay(OmsOrder order, Integer payType) {
        OmsOrderPay payEntity = new OmsOrderPay();
        payEntity.setOrderId(order.getId());
        payEntity.setPayAmount(order.getPayAmount());
        payEntity.setPayTime(new Date());
        payEntity.setPayType(payType);
        return payEntity;
    }

}
