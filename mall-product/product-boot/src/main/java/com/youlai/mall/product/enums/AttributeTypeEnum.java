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

    BASE_ATTRIBUTE(1, "基础属性"), // 重量、产地、上市时间等
    SALE_ATTRIBUTE(2, "销售属性"); // 影响价格的属性，如颜色、内存等

    AttributeTypeEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    @Getter
    private Integer value;

    @Getter
    private String label;

}
