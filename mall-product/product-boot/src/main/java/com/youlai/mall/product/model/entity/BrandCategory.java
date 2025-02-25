package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 品牌分类关联实体
 *
 * @author Ray.Hao
 * @since 2024-05-06
 */
@Getter
@Setter
@TableName("pms_brand_category")
public class BrandCategory implements Serializable {

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 品牌ID
     */
    private Long brandId;

}