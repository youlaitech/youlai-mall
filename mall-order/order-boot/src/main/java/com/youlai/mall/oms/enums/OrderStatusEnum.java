package com.youlai.mall.oms.enums;

import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 订单状态枚举
 *
 * @author haoxr
 * @since 2.0.0
 */
@Getter
public enum OrderStatusEnum implements IBaseEnum<Integer> {
    PENDING_PAYMENT(0, "待支付"),
    PENDING_SHIPMENT(1, "待发货"),
    PENDING_RECEIPT(2, "待收货"),
    COMPLETED(3, "已完成"),
    CANCELLED(4, "已取消"),
    RETURN_IN_PROGRESS(5, "退货中"),
    RETURNED(6, "已退货");


    OrderStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    private final Integer value;

    private final String label;

}
