package com.youlai.mall.sms.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author xinyi
 * @desc: 产品线枚举
 * @date 2021/6/26
 */
@Getter
@AllArgsConstructor
public enum ProductLineEnum {

    YOULAI("有来", 1),
    WUHUI("无回", 2),
    ;

    /**
     * 产品线描述
     */
    @JsonValue
    private String desc;

    /**
     * 产品线编码
     */
    @EnumValue
    private Integer code;

    public static ProductLineEnum of(String code) {
        Objects.requireNonNull(code);
        return Stream.of(values()).filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " code not exist"));
    }

}
