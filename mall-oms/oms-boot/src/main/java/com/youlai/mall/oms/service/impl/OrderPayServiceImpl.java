package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.RequestUtils;
import com.youlai.mall.oms.mapper.OrderPayMapper;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.oms.enums.PayTypeEnum;
import com.youlai.mall.oms.pojo.domain.OmsOrder;
import com.youlai.mall.oms.pojo.domain.OmsOrderItem;
import com.youlai.mall.oms.pojo.domain.OmsOrderPay;
import com.youlai.mall.oms.pojo.vo.PayVO;
import com.youlai.mall.oms.service.IOrderItemService;
import com.youlai.mall.oms.service.IOrderPayService;
import com.youlai.mall.oms.service.IOrderService;
import com.youlai.mall.pms.api.app.PmsSkuFeignService;
import com.youlai.mall.pms.pojo.dto.SkuLockDTO;
import com.youlai.mall.ums.api.UmsMemberFeignService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class OrderPayServiceImpl extends ServiceImpl<OrderPayMapper, OmsOrderPay> implements IOrderPayService {

    private IOrderService orderService;
    private UmsMemberFeignService memberFeignService;
    private IOrderItemService orderItemService;
    private PmsSkuFeignService skuFeignService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void pay(Long orderId) {

        //  查询订单状态
        OmsOrder order = orderService.getByOrderId(orderId);
        if (!OrderStatusEnum.PENDING_PAYMENT.getCode().equals(order.getStatus())) {
            throw new BizException("订单状态异常，请检查");
        }

        //  查询会员余额
        Long userId = RequestUtils.getUserId();
        Long balance = memberFeignService.getBalance(userId).getData();

        if (Long.compare(balance, order.getPayAmount()) == -1) {
            throw new BizException("会员余额不足");
        }

        //  更新用户余额
        memberFeignService.updateBalance(userId, order.getPayAmount());

        // 更新订单状态
        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setPayType(PayTypeEnum.BALANCE.getCode());
        order.setPayTime(new Date());
        orderService.updateById(order);

        // 扣减库存
        List<OmsOrderItem> orderItems = orderItemService.getByOrderId(orderId);
        List<SkuLockDTO> stockLick = orderItems.stream().map(orderItem -> SkuLockDTO.builder()
                .skuId(orderItem.getSkuId())
                .count(orderItem.getSkuQuantity())
                .build())
                .collect(Collectors.toList());

        skuFeignService.deductStock(stockLick);

        // 添加订单支付记录
        OmsOrderPay orderPay = OmsOrderPay.builder()
                .orderId(orderId)
                .payAmount(order.getPayAmount())
                .payTime(new Date())
                .payType(PayTypeEnum.BALANCE.getCode())
                .build();
        this.save(orderPay);
    }

    @Override
    public PayVO getByOrderId(Long orderId) {
        PayVO payVO = new PayVO();

        // 1、获取订单应支付金额
        OmsOrder omsOrder = orderService.getByOrderId(orderId);
        payVO.setPayAmount(omsOrder.getPayAmount());

        // 2、获取会员余额
        Long userId = RequestUtils.getUserId();
        Long balance = memberFeignService.getBalance(userId).getData();
        payVO.setBalance(balance);

        return payVO;
    }

}
