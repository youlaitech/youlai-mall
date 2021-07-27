package com.youlai.mall.pms.common.enums;

import lombok.Getter;

/**
 * 商品属性类型枚举
 *
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 */
public enum GoodsAttrTypeEnum {

    SPECIFICATION(1, "规格"),
    ATTRIBUTE(2, "属性");

    GoodsAttrTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Getter
    private Integer value;

    @Getter
    private String name;

    public static GoodsAttrTypeEnum getByValue(Integer value) {
        GoodsAttrTypeEnum goodsAttrTypeEnum = null;

        for (GoodsAttrTypeEnum item : values()) {
            if (item.getValue().equals(value)) {
                goodsAttrTypeEnum = item;
            }
        }
        return goodsAttrTypeEnum;
    }

}
