package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.youlai.common.base.BaseEntity;
import lombok.Data;

/**
 * 商品详情
 */
@TableName(value ="pms_spu_detail")
@Data
public class SpuDetail extends BaseEntity {

    /**
     * SPU ID
     */
    private Long spuId;

    /**
     * 商品详情
     */
    private String detail;
}