package com.youlai.mall.sms.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author xinyi
 * @desc: 优惠券分类
 * @date 2021/6/26
 */
@Getter
@AllArgsConstructor
public enum CouponCategoryEnum {

    MANJIAN("满减券", "001"),
    ZHEKOU("折扣券", "002"),
    LIJIAN("立减券", "003"),
    ;

    /**
     * 优惠券描述（分类）
     */
    @JsonValue
    private String desc;

    /**
     * 优惠券分类编码
     */
    @EnumValue
    private String code;

    public static CouponCategoryEnum of(String code) {
        Objects.requireNonNull(code);
        return Stream.of(values()).filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " code not exist"));
    }
}
