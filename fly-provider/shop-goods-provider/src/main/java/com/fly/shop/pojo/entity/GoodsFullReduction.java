package com.fly.shop.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("goods_full_reduction")
public class GoodsFullReduction implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long goodsId;

    private BigDecimal fullPrice;

    private BigDecimal reducePrice;



}