package com.fly4j.shop.marketing.pojo.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fly4j.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("m_seckill_goods_relation")
public class SeckillGoodsRelation extends BaseEntity {

    @TableId
    private Long id;

    private Long seckillId;

    private Long seckillSessionId;

    private Long goodsId;

    private BigDecimal seckillPrice;

    private Integer seckillCount;

    private Integer seckillLimit;

    private Integer sort;
}
