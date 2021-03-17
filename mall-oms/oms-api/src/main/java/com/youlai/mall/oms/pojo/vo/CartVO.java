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

        private String title; // 标题

        private String pic;

        private Integer count;

        private Long price;

        private Long coupon;

        private boolean checked;

    }
}
