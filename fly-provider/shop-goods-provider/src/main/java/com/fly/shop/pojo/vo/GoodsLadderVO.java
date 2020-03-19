package com.fly.shop.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class GoodsLadderVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private Long goodsId;

    private Integer count;

    private BigDecimal discount;

    private BigDecimal price;

}