package com.youlai.mall.member.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 购物车商品项
 *
 * @author Ray.Hao
 * @since 0.0.1
 */
@Data
public class CartItemDTO implements Serializable {

    /**
     * 商品库存ID
     */
    private Long skuId;

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 商品规格名称
     */
    private String skuName;

    /**
     * 商品图片
     */
    private String imgUrl;

    /**
     * 加
     */
    private Integer quantity;

    /**
     * 加购价格
     */
    private Long price;

    /**
     * 是否选中
     */
    private Boolean checked;

    /**
     * 商品库存
     */
    private Integer stock;

}
