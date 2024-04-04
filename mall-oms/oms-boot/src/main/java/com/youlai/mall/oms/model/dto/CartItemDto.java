package com.youlai.mall.oms.model.dto;

import lombok.Data;

import java.io.Serializable;
/**
 * 购物车商品项
 *
 * @author Ray Hao
 * @since 0.0.1
 */
@Data
public class CartItemDto implements Serializable {

    /**
     * 商品库存ID
     */
    private Long skuId;

    /**
     * 商品数量
     */
    private Integer count;

    /**
     * 是否选中
     */
    private Boolean checked;

}
