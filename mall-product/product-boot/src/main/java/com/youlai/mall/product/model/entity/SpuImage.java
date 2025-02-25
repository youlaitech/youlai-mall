package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.youlai.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品图片实体
 *
 * @author Ray.Hao
 * @since 2024-04-14
 */
@Getter
@Setter
@TableName("pms_spu_image")
public class SpuImage extends BaseEntity {

    /**
     * 商品ID
     */
    private Long spuId;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    private Integer isDeleted;
}
