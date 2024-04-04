package com.youlai.mall.pms.model.dto;

import lombok.Data;

/**
 * 商品库存信息DTO
 * <p>
 * 用于表示商品的库存信息。
 *
 * @author haoxr
 * @since 2.0.0
 */

@Data
public class SkuInfoDto {
    /**
     * SKU ID
     */
    private Long id;
    /**
     * SKU 编号
     */
    private String skuSn;
    /**
     * SKU 名称
     */
    private String skuName;
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
