package com.youlai.mall.oms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeataOrderDTO {

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 商品ID
     */
    private Long skuId;

    /**
     * 订单金额
     */
    private Long amount;


    private Boolean openEx;


}
