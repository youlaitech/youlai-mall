package com.youlai.mall.pms.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 商品验价传输层实体
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/2/7 22:52
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
