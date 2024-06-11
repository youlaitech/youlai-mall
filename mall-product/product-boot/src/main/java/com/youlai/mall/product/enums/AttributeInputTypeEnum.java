package com.youlai.mall.product.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 属性录入方式枚举
 *
 * @author Ray Hao
 * @since 2024/4/19
 */
@Getter
public enum AttributeInputTypeEnum implements IBaseEnum<Integer> {

    MANUAL(1, "手动录入"),
    SELECT(2, "可选列表");

    AttributeInputTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @EnumValue // 在将枚举存储到数据库时，存储 value 字段的值。 例如，MANUAL 存储为 1，SELECT 存储为 2。
    @JsonValue
    private final Integer value;

    private final String label;

}
