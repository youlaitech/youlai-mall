package com.youlai.mall.product.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 商品属性类型
 *
 * @author Ray Hao
 * @since 2024/5/23
 */
@Getter
public enum AttributeTypeEnum implements IBaseEnum<Integer> {

    BASE(1, "基础属性"),
    SALE(2, "销售属性");

    AttributeTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
    @EnumValue
    @JsonValue
    private final Integer value;


    private final String label;

}
