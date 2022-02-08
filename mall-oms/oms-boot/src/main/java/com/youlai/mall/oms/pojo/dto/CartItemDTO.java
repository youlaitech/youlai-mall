package com.youlai.mall.oms.pojo.dto;

import lombok.*;

import java.io.Serializable;

/**
 * 购物车商品传输层实体
 */
@Data
public class CartItemDTO implements Serializable {

    /**
     * 商品库存ID
     */
    private Long skuId;

    /**
     * 商品库存名称
     */
    private String skuName;

    /**
     * 商品编码
     */
    private String skuSn;

    /**
     * 商品图片
     */
    private String picUrl;

    /**
     * 商品数量
     */
    private Integer count;

    /**
     *  加入购物车时价格，因会变动，不能作为订单计算因子，订单提交时需验价
     */
    private Long price;

    /**
     * 优惠券金额
     */
    private Long coupon;

    /**
     * 是否选中
     */
    private Boolean checked;

    /**
     * 商品库存数量，页面控制能选择最大数量
     */
    private Integer stock;

    /**
     * 商品名称
     */
    private String spuName;

}
