package com.youlai.mall.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 订单商品验价传输对象
 *
 * @author Ray
 * @date 2022/2/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CheckPriceDTO {

    /**
     * 订单总金额
     */
    private Long totalFee;

    /**
     * 订单商品明细
     */
    private List<OrderSku> skus;

    /**
     * 订单商品对象
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderSku {
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
