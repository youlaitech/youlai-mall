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
     * 待付款
     */
    WAIT_PAY(1, "待付款"),
    /**
     * 待发货
     */
    WAIT_SHIPPING(2, "待发货"),
    /**
     * 已发货
     */
    SHIPPED(3, "已发货"),
    /**
     * 已完成
     */
    FINISHED(4, "已完成"),
    /**
     * 已关闭(退款完成)
     */
    CLOSED(5, "已关闭"),
    /**
     * 已取消
     */
    CANCELED(6, "已取消");


    OrderStatusEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    @Getter
    private Integer value;

    @Getter
    private String label;

}
