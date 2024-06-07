package com.youlai.mall.order.model.dto;

import lombok.Data;

/**
 * 订单商品
 *
 * @author Ray
 * @since 2.0.0
 */
@Data
public class OrderItemDTO {

    /**
     * 商品库存单元ID
     */
    private Long skuId;

    /**
     * SKU编码
     */
    private String skuCode;

    /**
     * SKU名称
     */
    private String skuName;

    /**
     * 商品图片地址
     */
    private String imgUrl;

    /**
     * 商品价格
     */
    private Long price;

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 订单商品数量
     */
    private Integer quantity;
}
