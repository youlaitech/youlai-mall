package com.youlai.mall.sms.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author xinyi
 * @desc
 * @date 2021/7/10
 */
@Getter
@AllArgsConstructor
public enum CouponTemplateStateEnum {

    INIT("待审核", 0),
    VERIFY("已审核",1),
    OFFER("发放中",2),

    USED("进行中", 1),
    FINISH("已过期(活动结束)", 2);

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

    public static CouponTemplateStateEnum of(Integer code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " Not Exist"));
    }
}
