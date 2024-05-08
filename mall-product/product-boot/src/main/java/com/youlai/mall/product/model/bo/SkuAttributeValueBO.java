package com.youlai.mall.product.model.bo;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * SKU规格值
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SkuAttributeValueBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * SKU ID
     */
    @TableId("sku_id")
    private Long skuId;

    /**
     * 规格值 ID
     */
    @TableId("spec_value_id")
    private Long specValueId;
}
