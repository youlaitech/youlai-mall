package com.fly4j.shop.marketing.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 秒杀商品选择传输层实体
 *
 * @author fly2021【xianrui0365@163.com】
 * @date 2020-03-31 10:32
 **/


@Data
public class SpikeGoodsDTO {

    /**
     * 商品ID
     */
    private Long goodsId;

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
