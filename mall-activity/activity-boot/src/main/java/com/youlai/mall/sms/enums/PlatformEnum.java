package com.youlai.mall.sms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

@Getter
public enum PlatformEnum implements IBaseEnum<Integer> {

    ALL(0, "全平台"),
    APP(1, "APP"),
    PC(2, "PC"),
    ;

    @Getter
    @EnumValue //  Mybatis-Plus 提供注解表示插入数据库时插入该值
    private Integer value;

    @Getter
    @JsonValue //  表示对枚举序列化时返回此字段
    private String label;

    PlatformEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
