package com.youlai.mall.pms.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 商品验价传输对象
 *
 * @author haoxr
 * @date 2022/2/7
 */
@Data
public class CheckPriceDTO {

    /**
     * 订单商品总金额
     */
    private Long orderTotalAmount;

    /**
     * 订单商品明细
     */
    private List<CheckSku> checkSkus;


    /**
     * 订单商品明细
     */
    @Data
    public static class CheckSku {
        /**
         * 商品ID
         */
        private Long skuId;

        /**
         * 商品数量
         */
        private Integer count;
    }


}
