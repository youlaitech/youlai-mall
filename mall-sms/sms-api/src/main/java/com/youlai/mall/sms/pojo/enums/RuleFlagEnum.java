package com.youlai.mall.sms.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xinyi
 * @desc: 结算规则类型枚举
 * @date 2021/7/11
 */
@Getter
@AllArgsConstructor
public enum  RuleFlagEnum {
    // 单类型优惠券定义
    MANJIAN("满减券计算规则"),

    ZHEKOU("折扣卷计算规则"),

    LIJIAN("立减券计算规则"),

    // 多类别组合优惠券定义
    MANJIAN_ZHEKOU("满减券+折扣券计算规则"),
    ;

    /**
     * 规则描述
     */
    private String desc;
}
