package com.youlai.mall.pms.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 锁定库存传输对象
 *
 * @author haoxr
 * @date 2022/12/20
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LockStockDTO {

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 锁定商品集合
     */
    private List<LockedSku> lockedSkus;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LockedSku {

        /**
         * 锁定商品ID
         */
        private Long skuId;

        /**
         * 商品数量
         */
        private Integer count;

    }

}
