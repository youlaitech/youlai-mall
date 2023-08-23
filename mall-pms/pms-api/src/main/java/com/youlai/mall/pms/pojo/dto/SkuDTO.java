package com.youlai.mall.pms.pojo.dto;

import lombok.Data;

/**
 * 商品库存
 *
 * @author haoxr
 * @since 2.0.0
 */

@Data
public class SkuDTO {
    /**
     * skuId
     */
    private Long skuId;
    /**
     * SKU 编号
     */
    private String skuSn;
    /**
     * SKU 名称
     */
    private String skuName;
    /**
     * SKU 图片地址
     */
    private String picUrl;
    /**
     * SKU 价格
     */
    private Long price;
    /**
     * SKU 库存数量
     */
    private Integer stockNum;
    /**
     * SPU 名称
     */
    private String spuName;
}
