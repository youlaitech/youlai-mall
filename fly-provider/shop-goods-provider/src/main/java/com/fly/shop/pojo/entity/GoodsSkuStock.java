package com.fly.shop.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("goods_sku_stock")
public class GoodsSkuStock implements Serializable {
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