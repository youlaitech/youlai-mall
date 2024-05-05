package com.youlai.mall.product.model.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * SKU规格值 VO
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SkuSpecValueVO implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * SKU ID
         */

    private Long skuId;

        /**
         * 规格值 ID
         */

    private Long specValueId;
}
