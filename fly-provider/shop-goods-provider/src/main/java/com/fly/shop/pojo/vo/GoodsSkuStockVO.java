package com.fly.shop.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class GoodsSkuStockVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long goodsId;

    private String skuCode;

    private BigDecimal price;

    private Integer stock;

    private Integer lowStock;

    private String spData;

    private String pic;

    private Integer sale;

    private BigDecimal promotionPrice;

    private Integer lockStock;

}