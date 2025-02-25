package com.youlai.mall.member.model.cache;

import lombok.Data;

import java.io.Serializable;

/**
 * 购物车商品项
 *
 * @author Ray.Hao
 * @since 0.0.1
 */
@Data
public class CartItemCache implements Serializable {

    /**
     * 商品库存ID，用于标识购物车中每件商品的唯一库存条目
     */
    private Long skuId;

    /**
     * 购物车中商品的数量·
     */
    private Integer quantity;

    /**
     * 是否选中购物车中的商品进行结算
     */
    private Boolean checked;

}
