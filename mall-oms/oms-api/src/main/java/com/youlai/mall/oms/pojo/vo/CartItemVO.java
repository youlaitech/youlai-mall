package com.youlai.mall.oms.pojo.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 购物车项目项实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Accessors(chain = true)
public class CartItemVO implements Serializable {

    private Long skuId;

    private String skuPic;

    private String skuName;

    private Integer inventory;

    private Integer count;

    private Long price;

    private Long coupon;

    private Long subtotal;

    private boolean hasInventory;

    private boolean checked;

}
