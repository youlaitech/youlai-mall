package com.youlai.mall.product.enums;

import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 商品排序字段枚举
 *
 * @author Ray Hao
 * @since 2024/5/23
 */
@Getter
public enum ProductOrderByEnum implements IBaseEnum<String> {

    SCORE("score", "评分"),
    PRICE("price", "价格"),
    SALES("sales", "销量");

    ProductOrderByEnum(String column, String label) {
        this.value = column;
        this.label = label;
    }

    private final String value;

    private final String label;

    @Override
    public String toString(){
        return this.value;
    }

}
