package com.youlai.mall.oms.pojo.vo;

import com.youlai.common.base.BaseVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单商品
 */
@Builder
public class OrderItemVO extends BaseVO {

    /**
     * 商品id
     */
    @Getter
    @Setter
    private Long skuId;

    /**
     * 商品图片
     */
    @Getter
    @Setter
    private String skuImg;

    /**
     * 商品名称
     */
    @Getter
    @Setter
    private String skuName;

    /**
     * 商品数量
     */
    @Getter
    @Setter
    private Integer num;

    /**
     * 商品单价
     */
    @Getter
    @Setter
    private Long price;

    @Getter
    @Setter
    private Long coupon = 0L;

    /**
     * 小计
     */
    @Setter
    private Long subtotal;

    public Long getSubtotal() {
        Long total = 0L;
        if (price != null && num != null){
            total = price * num;
        }
        return total;
    }
}
