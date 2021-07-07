package com.youlai.mall.sms.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author xinyi
 * @desc：有效期类型枚举
 * @date 2021/6/26
 */
@Getter
@AllArgsConstructor
public enum PeriodTypeEnum {

    REGULAR("固定的(固定日期)",1),
    SHIFT("变动的(以领取之日开始计算)",2),

    ;

    /**
     * 有效期描述
     */
    @JsonValue
    private String desc;

    /**
     * 有效期编码
     */
    @EnumValue
    private Integer code;

    public static PeriodTypeEnum of(Integer code) {
        Objects.requireNonNull(code);
        return Stream.of(values()).filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " code not exist"));
    }
}
