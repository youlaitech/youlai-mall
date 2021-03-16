package com.youlai.mall.oms.pojo.vo;

import com.youlai.common.base.BaseVO;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单商品
 */
@Builder
@Data
public class OrderItemVO extends BaseVO {

    private Long skuId;
    private String skuImg;
    private String skuName;
    private Integer count;
    private Long price;
    private Long coupon;


}
