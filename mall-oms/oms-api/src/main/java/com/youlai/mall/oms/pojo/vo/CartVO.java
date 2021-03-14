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
public class CartVO implements Serializable {

    private List<CartItemVO> items;

    private Integer totalNum;

    private Long totalPrice;

    private Long totalCoupon;

    public List<CartItemVO> getItems() {
        return items;
    }

    public void setItems(List<CartItemVO> items) {
        this.items = items;
    }

    public Integer getTotalNum() {
        int total = 0;
        if (items != null && items.size() > 0) {
            total = items.stream().filter(CartItemVO::isChecked).mapToInt(CartItemVO::getNum).sum();
        }
        return total;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Long getTotalPrice() {
        long total = 0L;
        if (items != null && items.size() > 0) {
            total = items.stream().filter(CartItemVO::isChecked).mapToLong(CartItemVO::getSubtotal).sum();
        }
        return total;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getTotalCoupon() {
        long total = 0L;
//        if (items != null && items.size() > 0) {
//            total = items.stream().filter(CartItemVo::isChecked).mapToLong(CartItemVo::getCoupon).sum();
//        }
        return total;
    }

    public void setTotalCoupon(Long totalCoupon) {
        this.totalCoupon = totalCoupon;
    }
}
