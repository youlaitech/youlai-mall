package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

/**
 * SKU 属性值实体对象
 *
 * @author Ray.Hao
 * @since 2024/4/14
 */
@TableName("pms_sku_spec")
@Data
public class SkuSpec implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
