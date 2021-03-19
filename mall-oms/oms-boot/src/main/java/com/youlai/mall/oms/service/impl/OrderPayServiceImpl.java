package com.youlai.mall.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.result.Result;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.RequestUtils;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.oms.enums.PayTypeEnum;
import com.youlai.mall.oms.mapper.OrderPayMapper;
import com.youlai.mall.oms.pojo.domain.OmsOrder;
import com.youlai.mall.oms.pojo.domain.OmsOrderPay;
import com.youlai.mall.oms.service.ICartService;
import com.youlai.mall.oms.service.IOrderPayService;
import com.youlai.mall.oms.service.IOrderService;
import com.youlai.mall.pms.api.app.PmsSkuFeignService;
import com.youlai.mall.ums.api.UmsMemberFeignService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;


@Slf4j
@Service
@AllArgsConstructor
public class OrderPayServiceImpl extends ServiceImpl<OrderPayMapper, OmsOrderPay> implements IOrderPayService {

    private IOrderService orderService;
    private UmsMemberFeignService memberFeignService;
    private PmsSkuFeignService skuFeignService;
    private ICartService cartService;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean pay(Long orderId) {

        OmsOrder order = orderService.getByOrderId(orderId);
        if (order != null && !OrderStatusEnum.PENDING_PAYMENT.getCode().equals(order.getStatus())) {
            throw new BizException("支付失败，请检查订单状态");
        }

        // 扣减余额
        Long userId = RequestUtils.getUserId();
        Long payAmount = order.getPayAmount();
        Result deductBalanceResult = memberFeignService.deductBalance(userId, payAmount);
        if (!Result.isSuccess(deductBalanceResult)) {
            throw new BizException("扣减账户余额失败");
        }

        // 扣减库存
        Result deductStockResult = skuFeignService.deductStock(order.getOrderSn());
        if (!Result.isSuccess(deductStockResult)) {
            throw new BizException("扣减商品库存失败");
        }

        // 更新订单状态
        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setPayType(PayTypeEnum.BALANCE.getCode());
        order.setPayTime(new Date());
        orderService.updateById(order);

        // 支付成功删除购物车已勾选的商品
        cartService.removeCheckedItem();

        return true;
    }
}
