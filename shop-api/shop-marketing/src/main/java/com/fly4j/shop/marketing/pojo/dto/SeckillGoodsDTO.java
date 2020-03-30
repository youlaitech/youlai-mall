package com.fly4j.shop.marketing.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class SeckillGoodsDTO {

    /**
     * 商品ID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 货号
     */
    private String goodsSn;

    /**
     * 价格
     */
    private BigDecimal goodsPrice;

    /**
     * 库存数量
     */
    private Integer goodsStock;

}
