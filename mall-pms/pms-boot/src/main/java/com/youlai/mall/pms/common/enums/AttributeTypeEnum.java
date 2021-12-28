package com.youlai.mall.pms.common.enums;

import lombok.Getter;

/**
 * 商品属性类型枚举
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 */
public enum AttributeTypeEnum {

    SPECIFICATION(1, "规格"),
    ATTRIBUTE(2, "属性");

    AttributeTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Getter
    private Integer value;

    @Getter
    private String name;

    public static AttributeTypeEnum getByValue(Integer value) {
        AttributeTypeEnum attributeTypeEnum = null;

        for (AttributeTypeEnum item : values()) {
            if (item.getValue().equals(value)) {
                attributeTypeEnum = item;
            }
        }
        return attributeTypeEnum;
    }

}
