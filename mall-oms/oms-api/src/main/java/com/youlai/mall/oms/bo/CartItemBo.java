package com.youlai.mall.oms.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class CartItemBo {

    /**
     * 商品sku id
     */
    @NotNull(message = "商品id不能为空")
    private Long skuId;

    /**
     * 商品数量
     */
    @NotNull(message = "商品数量不能为空")
    @Min(value = 0, message = "商品数量不能为负数")
    private Integer number;

}
