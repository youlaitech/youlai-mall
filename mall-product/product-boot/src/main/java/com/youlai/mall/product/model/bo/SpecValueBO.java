package com.youlai.mall.product.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * SKU 属性值实体对象
 *
 * @author Ray
 * @since 2024/4/14
 */
@TableName("pms_sku_spec_value")
@Data
public class SpecValueBO implements Serializable {

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
     * 规格ID
     */
    private Long specId;

    /**
     * 规格名称 如：颜色
     */
    private String specName;

    /**
     * 规格值 如：红色
     */
    private String value;
}
