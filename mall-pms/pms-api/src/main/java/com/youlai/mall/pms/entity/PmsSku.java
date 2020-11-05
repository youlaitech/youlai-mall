package com.youlai.mall.pms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PmsSku extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long spuId;
    private String specification;
    private String barCode;
    private String pic;
    private Integer originPrice;
    private Integer price;
    private Integer vipPrice;
    private Integer stock;
}
