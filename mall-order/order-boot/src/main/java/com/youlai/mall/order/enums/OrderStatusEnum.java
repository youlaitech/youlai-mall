package com.youlai.mall.order.enums;

import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 订单状态枚举
 *
 * @author Ray Hao
 * @since 2.0.0
 */
@Getter
public enum OrderStatusEnum implements IBaseEnum<Integer> {

    PENDING_PAYMENT(0, "待支付"),
    PENDING_SHIPMENT(1, "待发货"),
    PENDING_RECEIPT(2, "待收货"),
    COMPLETED(3, "交易完成"), // 用户确认收货
    CANCELLED(4, "已取消"),
    RETURN_IN_PROGRESS(5, "退货中"),
    RETURNED(6, "已退货"),
    REFUND_PENDING(7, "退款处理中"),
    REFUNDED(8, "已退款"),
    EVALUATING(9, "待评价"), // 用户确认收货后，如果商品支持评价，则状态变为待评价
    EVALUATED(10, "已评价");

    OrderStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    private final Integer value;

    private final String label;

}
