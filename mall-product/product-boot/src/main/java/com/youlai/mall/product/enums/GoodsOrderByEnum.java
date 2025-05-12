package com.youlai.mall.product.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 商品排序字段枚举
 *
 * @author Ray.Hao
 * @since 2024/5/23
 */
@Getter
public enum GoodsOrderByEnum implements IBaseEnum<String>{

    SCORE("comprehensive", "综合"),
    PRICE("price", "价格"),
    SALES("sales", "销量");

    GoodsOrderByEnum(String column, String label) {
        this.value = column;
        this.label = label;
    }

    @EnumValue
    private final String value;

    private final String label;

    @Override
    @JsonValue
    public String toString() {
        return value;
    }

}
