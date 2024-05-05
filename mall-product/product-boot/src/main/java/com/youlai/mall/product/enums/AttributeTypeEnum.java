package com.youlai.mall.product.enums;

import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 商品属性类型枚举
 *
 * @author haoxr
 * @since 2022/12/20
 */
public enum AttributeTypeEnum implements IBaseEnum<Integer> {

    SPECIFICATION(1, "规格"), //  规格: 影响价格的属性，如尺寸、颜色等
    ATTRIBUTE(2, "参数"); //  参数: 重量、产地、包装等

    AttributeTypeEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    @Getter
    private Integer value;

    @Getter
    private String label;

}
