package com.fly4j.shop.marketing.pojo.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@TableName("m_spike_period_goods")
@Accessors(chain = true)
public class SpikePeriodGoodsEntity extends BaseEntity {

    @TableId
    private Long id;

    private Long spikeId;

    private Long spikePeriodId;

    private Long goodsId;

    private BigDecimal spikePrice;

    private Integer spikeCount;

    private Integer spikeLimit;

    private Integer sort;
}
