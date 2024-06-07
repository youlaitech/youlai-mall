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
    CANCELLED(3, "已取消"),
    RETURN_IN_PROGRESS(4, "退货中"),
    RETURNED(5, "已退货"),
    REFUNDED(6, "已退款");

    OrderStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    private final Integer value;

    private final String label;

}
