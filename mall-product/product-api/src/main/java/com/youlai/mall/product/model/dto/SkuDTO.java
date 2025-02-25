package com.youlai.mall.product.model.dto;

import lombok.Data;

/**
 * SKU 传输对象
 *
 * @author Ray.Hao
 * @since 2.0.0
 */

@Data
public class SkuDTO {
    /**
     * SKU ID
     */
    private Long id;
    /**
     * SKU 编号
     */
    private String code;
    /**
     * SKU 名称
     */
    private String name;
    /**
     * SKU 展示图片URL
     */
    private String imgUrl;
    /**
     * SKU 价格
     */
    private Long price;
    /**
     * SKU 库存数量
     */
    private Integer stock;
    /**
     * 所属SPU的名称
     */
    private String spuName;
}
