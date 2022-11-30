package com.youlai.mall.oms.enums;

import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 订单状态枚举
 *
 * @author haoxr
 * @date 2022/11/28
 */
public enum OrderStatusEnum implements IBaseEnum<Integer> {

    /**
     * 1. 订单创建阶段
     */
    PENDING_PAYMENT(1, "待支付"),
    USER_CANCEL(2, "用户取消"),
    SYSTEM_CANCEL(3, "系统自动取消"),

    /**
     * 2. 订单付款阶段
     */
    PAYED(10, "已支付"),
    APPLY_REFUND(11, "申请退款"),
    REFUNDED(12, "已退款"),

    /**
     * 订单发货阶段
     */
    DELIVERED(20, "已发货"),

    /**
     * 订单收货阶段
     */
    USER_RECEIVE(30, "用户收货"),
    AUTO_RECEIVE(31, "系统自动收货"),

    /**
     * 订单完结
     */
    COMPLETED(99, "已完成");


    OrderStatusEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    @Getter
    private Integer value;

    @Getter
    private String label;

}
