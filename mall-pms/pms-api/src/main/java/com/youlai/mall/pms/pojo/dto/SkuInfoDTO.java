package com.youlai.mall.pms.pojo.dto;

import lombok.Data;

/**
 * 商品库存信息DTO（Data Transfer Object）
 * <p>
 * 用于表示商品的库存信息。
 * <p>
 * SkuInfoDto包含以下属性：
 * <p>
 * skuId: SKU的唯一标识符
 * skuSn: SKU的编号
 * skuName: SKU的名称
 * picUrl: SKU的图片地址
 * price: SKU的价格
 * stockNum: SKU的库存数量
 * spuName: 所属SPU（Standard Product Unit）的名称
 * 该类用于在不同层之间传输商品库存信息的对象，以便于数据的传递和处理。
 *
 * @author haoxr
 * @since 2.0.0
 */

@Data
public class SkuInfoDTO {
    /**
     * SKU的唯一标识符
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
     * 所属SPU的名称
     */
    private String spuName;
}
