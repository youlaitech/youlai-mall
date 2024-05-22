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
 * @author Ray Hao
 * @since 2024/4/14
 */
@TableName("pms_sku_attribute_value")
@Data
public class SkuAttributeValue implements Serializable {

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
     * 规格属性ID
     */
    private Long attributeId;

    /**
     * 规格属性名称
     */
    private String attributeName;

    /**
     * 规格属性值
     */
    private String attributeValue;
}
