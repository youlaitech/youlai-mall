package com.youlai.mall.pms.common.enums;

import lombok.Getter;

/**
 * 商品属性类型枚举
 *
 * @author haoxr
 * @date 2022/12/20
 */
public enum AttributeTypeEnum {

    SPEC(1, "规格"),
    ATTR(2, "属性");

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
