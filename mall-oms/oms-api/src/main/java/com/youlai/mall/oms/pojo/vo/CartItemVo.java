package com.youlai.mall.oms.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

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

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 商品单价
     */
    private Long price;

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
    private boolean choose;

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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSubTotal() {
        long total = 0;
        if (price != null && num != null) {
            total = price * num;
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

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }
}
