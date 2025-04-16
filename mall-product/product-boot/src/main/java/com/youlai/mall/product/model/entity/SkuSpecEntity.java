package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;

import com.youlai.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * SKU 属性值实体对象
 *
 * @author Ray.Hao
 * @since 2024/4/14
 */
@TableName("pms_sku_spec")
@Getter
@Setter
public class SkuSpecEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 规格名称
     */
    private String specName;

    /**
     * 规格值
     */
    private String specValue;

    /**
     * 规格图片，只有主规格才有，如：红色为红色图片
     */
    private String imgUrl;
}
