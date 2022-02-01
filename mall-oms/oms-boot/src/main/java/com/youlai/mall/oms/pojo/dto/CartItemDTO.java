package com.youlai.mall.oms.pojo.dto;

import lombok.*;

import java.io.Serializable;

/**
 * 购物车商品传输层实体
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Data
public class CartItemDTO implements Serializable {


    private Long skuId;

    /**
     * 商品库存单元名称
     */
    private String skuName;

    /**
     * 商品库存单元编码
     */
    private String skuSn;

    /**
     * 商品库存单元图片
     */
    private String picUrl;

    private Integer count; // 商品数量

    /**
     *  加入购物车价格，因会变动，不能作为订单计算因子，订单验价时需重新获取商品价格即可
     */
    private Long price;

    private Long coupon;

    private Boolean checked;

    /**
     * 商品库存数量，页面控制能选择最大数量
     */
    private Integer stock;

    /**
     * 商品名称
     */
    private String goodsName;

}
