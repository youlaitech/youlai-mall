package com.youlai.mall.oms.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车项目项实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartItemVo implements Serializable {

    /**
     * 商品id
     */
    private Long skuId;

    /**
     * 商品图片
     */
    private String skuImg;

    /**
     * 商品名称
     */
    private String skuName;

    private Integer stock;

    /**
     * 商品数量
     */
    private Integer number;

    /**
     * 商品单价
     */
    private Long price;

    private Long coupon = 0L;

    /**
     * 小计
     */
    private Long subTotal;

    /**
     * 是否有库存
     */
    private boolean hasStock;

    /**
     * 是否选择
     */
    private boolean checked;

    private List<String> skuAttrs;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuImg() {
        return skuImg;
    }

    public void setSkuImg(String skuImg) {
        this.skuImg = skuImg;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getCoupon() {
        return coupon;
    }

    public void setCoupon(Long coupon) {
        this.coupon = coupon;
    }

    public Long getSubTotal() {
        long total = 0;
        if (price != null && number != null) {
            total = price * number;
        }
        return total;
    }

    public void setSubTotal(Long subTotal) {

        this.subTotal = subTotal;
    }

    public boolean isHasStock() {
        return hasStock;
    }

    public void setHasStock(boolean hasStock) {
        this.hasStock = hasStock;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<String> getSkuAttrs() {
        return skuAttrs;
    }

    public void setSkuAttrs(List<String> skuAttrs) {
        this.skuAttrs = skuAttrs;
    }
}
