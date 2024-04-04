package com.youlai.mall.ums.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 购物车商品项
 *
 * @author Ray Hao
 * @since 0.0.1
 */
@Data
public class CartItemVo implements Serializable {

    /**
     * 商品库存ID
     */
    private Long skuId;

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 规格集合
     */
    private Set<String> specs;

    /**
     * 商品图片
     */
    private String imageUrl;

    /**
     * 加购数量·
     */
    private Integer count;

    /**
     * 加购价格
     */
    private Long price;

    /**
     * 是否选中
     */
    private Boolean checked;

    /**
     * 商品库存
     */
    private Integer stock;

}
