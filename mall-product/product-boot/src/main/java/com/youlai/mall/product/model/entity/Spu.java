package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SPU 实体类
 *
 * @author Ray.Hao
 * @since 2022/7/2
 */
@EqualsAndHashCode(callSuper = false)
@TableName("pms_spu")
@Data
public class Spu extends BaseEntity {

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品类别ID
     */
    private Long categoryId;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 主图URL
     */
    private String imgUrl;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 上架状态（1：上架，0：下架）
     */
    private Integer status;

    /**
     * 商品描述
     */
    private String description;


    /**
     * 逻辑删除标识(0-未删除 1-已删除)
     */
    private Integer isDeleted;

}
