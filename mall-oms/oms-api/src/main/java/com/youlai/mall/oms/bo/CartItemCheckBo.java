package com.youlai.mall.oms.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class CartItemCheckBo {

    /**
     * 商品sku id
     */
    @NotNull(message = "商品id不能为空")
    private Long skuId;

    /**
     * 商品数量
     */
    private Integer check = 1;

}
