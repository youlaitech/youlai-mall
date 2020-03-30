package com.fly4j.shop.marketing.pojo.dto;


import com.fly4j.shop.marketing.pojo.entity.SeckillGoodsRelation;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeckillGoodsRelationDTO extends SeckillGoodsRelation {

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
