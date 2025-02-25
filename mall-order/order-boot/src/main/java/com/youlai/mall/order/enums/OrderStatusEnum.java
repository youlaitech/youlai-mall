package com.youlai.mall.order.enums;

import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 订单状态枚举
 *
 * @author Ray.Hao
 * @since 2.0.0
 */
@Getter
public enum OrderStatusEnum implements IBaseEnum<Integer> {

    // 支付阶段
    WAIT_PAY(0, "待支付"),           // 订单已生成，待用户支付
    WAIT_REFUND(7, "退款处理中"),     // 用户发起退款申请，退款处理中
    REFUNDED(8, "已退款"),            // 退款已完成

    // 发货阶段
    WAIT_SHIP(1, "待发货"),           // 用户已支付，等待商家发货
    WAIT_RECEIVE(2, "待收货"),        // 商品已发货，待用户签收
    FINISHED(3, "交易完成"),          // 用户确认收货，交易完成
    CANCELLED(4, "已取消"),           // 订单已取消（用户取消或超时）

    // 售后阶段
    RETURNING(5, "退货中"),           // 用户申请退货，退货处理中
    RETURNED(6, "已退货"),            // 退货完成

    // 评价阶段
    WAIT_REVIEW(9, "待评价"),          // 用户确认收货后，待评价
    REVIEWED(10, "已评价");            // 用户已评价


    OrderStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    private final Integer value;

    private final String label;

}
