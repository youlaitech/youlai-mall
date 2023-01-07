package com.youlai.mall.oms.common.enums;

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
     * 待付款
     */
    UNPAID(0, "待付款"),
    /**
     * 已付款(待发货)
     */
    PAID(1, "已付款"),
    /**
     * 已发货
     */
    SHIPPED(2, "已发货"),
    /**
     * 已完成
     */
    COMPLETE(3, "已完成"),
    /**
     * 已取消
     */
    CANCELED(4, "已取消"),
    /**
     * 售后中
     */
    SERVICING(5, "售后中")
    ;

    OrderStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @Getter
    private Integer value;

    @Getter
    private String label;

}
