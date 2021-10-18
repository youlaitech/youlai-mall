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
public enum CouponUsedStateEnum {

    INIT("未生效", 0),
    GOING("进行中",1),
    FINISH("已结束",2);

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

    public static CouponUsedStateEnum of(Integer code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " Not Exist"));
    }
}
