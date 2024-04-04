package com.youlai.mall.sms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

@Getter
public enum ValidityPeriodTypeEnum implements IBaseEnum<Integer> {

    UNKNOWN(0, null),
    DATE_RANGE(1, "日期范围"),
    FIXED_DAYS(2, "固定天数"),
    ;
    @Getter
    @EnumValue //  Mybatis-Plus 提供注解表示插入数据库时插入该值
    private Integer value;

    @Getter
    @JsonValue //  表示对枚举序列化时返回此字段
    private String label;

    ValidityPeriodTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
