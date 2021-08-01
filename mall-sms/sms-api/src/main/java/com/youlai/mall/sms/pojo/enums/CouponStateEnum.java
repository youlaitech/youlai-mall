package com.youlai.mall.sms.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author huawei
 * @desc：用户优惠券状态
 * @email huawei_code@163.com
 * @date 2021/2/28
 */
@Getter
@AllArgsConstructor
public enum  CouponStateEnum {

    USABLE("可用", 1),
    USED("已使用", 5),
    OCCUPY("占用中(下单未付款)", 3),
    EXPIRED("已过期(未被使用)", 10);

    /**
     * 用户优惠券状态描述
     */
    @JsonValue
    private String desc;

    /**
     * 用户优惠券状态码
     */
    @EnumValue
    private Integer code;

    public static CouponStateEnum of(Integer code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " Not Exist"));
    }
}
