package com.fly4j.shop.goods.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("goods_ladder")
public class GoodsLadder implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private Long goodsId;

    private Integer count;

    private BigDecimal discount;

    private BigDecimal price;

}