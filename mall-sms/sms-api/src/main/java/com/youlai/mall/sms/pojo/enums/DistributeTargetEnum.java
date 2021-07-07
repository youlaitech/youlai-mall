package com.youlai.mall.sms.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author xinyi
 * @desc：优惠券分发目标枚举
 * @date 2021/6/26
 */
@Getter
@AllArgsConstructor
public enum DistributeTargetEnum {

    SINGLE("单用户",1),
    MULTI("多用户",2),
    ;

    /**
     * 分发目标描述
     */
    @JsonValue
    private String desc;

    /**
     * 分发目标编码
     */
    @EnumValue
    private Integer code;

    public static DistributeTargetEnum of(Integer code) {
        Objects.requireNonNull(code);
        return Stream.of(values()).filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " code not exist"));
    }
}
