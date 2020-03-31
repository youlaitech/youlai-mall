package com.fly4j.shop.marketing.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpikePeriodGoodsDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 商品ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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

    /**
     * 秒杀价格
     */
    private BigDecimal spikePrice;

    /**
     * 秒杀数量
     */
    private Integer spikeCount;

    /**
     * 限购数量
     */
    private Integer spikeLimit;

    /**
     * 排序
     */
    private Integer sort;

}
