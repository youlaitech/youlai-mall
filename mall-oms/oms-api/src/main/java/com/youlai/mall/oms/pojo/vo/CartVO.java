package com.youlai.mall.oms.pojo.vo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Data
public class CartVO implements Serializable {

    private List<CartItem> items;

    @Data
    public static class CartItem {

        private Long skuId;

        private String skuName; // 标题

        private String skuCode;

        private String pic;

        private Integer count; // 商品数量

        private Long price; // 加入购物车价格，因会变动，不能作为订单计算因子，订单验价时需重新获取商品价格即可

        private Long coupon;

        private Boolean checked;

        private Integer stock;// 商品库存数量，页面控制能选择最大数量

        private String spuName;

    }
}
