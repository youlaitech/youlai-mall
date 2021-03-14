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
import com.youlai.mall.oms.service.OrderLogsService;
import com.youlai.mall.oms.service.OrderPayService;
import com.youlai.mall.oms.service.IOrderService;
import com.youlai.mall.ums.api.app.MemberFeignService;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;


@Slf4j
@AllArgsConstructor
@Service("orderPayService")
public class OrderPayServiceImpl extends ServiceImpl<OrderPayDao, OmsOrderPay> implements OrderPayService {

    private IOrderService IOrderService;

    private OrderLogsService orderLogsService;

    private MemberFeignService memberFeignService;

    @Override
    public PayInfoVO info(String orderId) {
        Long userId = RequestUtils.getUserId();
        PayInfoVO payInfoVO = new PayInfoVO();
        // 1、获取订单应支付金额
        OmsOrder omsOrder = IOrderService.getByOrderId(orderId);
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

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void balancePay(String orderId) {
        // 1、查询订单详情，判断订单状态是否是待支付状态
        log.info("订单进入支付流程，orderId：{}", orderId);
        OmsOrder order = IOrderService.getByOrderId(orderId);
        OrderStatusEnum orderStatusEnum =  OrderStatusEnum.getValue(order.getStatus()) ;
        if (orderStatusEnum != OrderStatusEnum.NEED_PAY) {
            log.error("订单状态异常无法支付，orderStatus={}", orderStatusEnum.getText());
            throw new BizException("订单" + orderStatusEnum.getText());
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
        order.setStatus(OrderStatusEnum.IS_PAY.getCode());
        order.setPayTime(new Date());
        order.setPayType(PayTypeEnum.BALANCE.getCode());
        IOrderService.updateById(order);
        this.save(createOrderPay(order, PayTypeEnum.BALANCE.getCode()));
        orderLogsService.addOrderLogs(order.getId(), OrderStatusEnum.IS_PAY.getCode(), userId.toString(), "支付订单");
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
