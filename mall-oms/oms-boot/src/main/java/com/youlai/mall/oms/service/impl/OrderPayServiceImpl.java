package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.RequestUtils;
import com.youlai.mall.oms.dao.OrderPayDao;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.oms.enums.PayTypeEnum;
import com.youlai.mall.oms.pojo.domain.OmsOrder;
import com.youlai.mall.oms.pojo.domain.OmsOrderPay;
import com.youlai.mall.oms.pojo.vo.PayInfoVO;
import com.youlai.mall.oms.service.IOrderLogService;
import com.youlai.mall.oms.service.IOrderPayService;
import com.youlai.mall.oms.service.IOrderService;
import com.youlai.mall.ums.api.app.MemberFeignService;
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
    public void payWithBalance(Long orderId) {

        // 1. 查询订单详情，判断订单状态是否是待支付状态
        OmsOrder order = orderService.getByOrderId(orderId);
        if (!OrderStatusEnum.PENDING_PAYMENT.getCode().equals(order.getStatus())) {
            throw new BizException("订单" + OrderStatusEnum.getValue(order.getStatus()).getText());
        }

        // 2. 远程调用查询会员余额
        Long userId = RequestUtils.getUserId();
        Long balance = memberFeignService.getBalance(userId).getData();

        if (Long.compare(balance, order.getPayAmount()) == -1) {
            throw new BizException("会员余额不足，请先充值");
        }

        // 3. 更新用户余额
        memberFeignService.updateBalance(userId, order.getPayAmount());

        // 4. 更新订单状态
        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setPayTime(new Date());
        order.setPayType(PayTypeEnum.BALANCE.getCode());
        orderService.updateById(order);


        // 5. 添加订单支付记录
        OmsOrderPay orderPay = OmsOrderPay.builder()
                .orderId(orderId)
                .payAmount(order.getPayAmount())
                .payTime(new Date())
                .payType(PayTypeEnum.BALANCE.getCode())
                .build();

        this.save(orderPay);

        // 6. 添加操作日志
        orderLogService.addOrderLogs(order.getId(), OrderStatusEnum.PAID.getCode(), userId.toString(), "支付订单");

    }

    @Override
    public PayInfoVO getPayInfo(Long orderId) {
        PayInfoVO payInfoVO = new PayInfoVO();

        // 1、获取订单应支付金额
        OmsOrder omsOrder = orderService.getByOrderId(orderId);
        payInfoVO.setPayAmount(omsOrder.getPayAmount());

        // 2、获取会员余额
        Long userId = RequestUtils.getUserId();
        Long balance = memberFeignService.getBalance(userId).getData();
        payInfoVO.setBalance(balance);

        return payInfoVO;
    }

}
