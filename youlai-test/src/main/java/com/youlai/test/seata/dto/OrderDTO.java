package com.youlai.test.seata.dto;

import lombok.Data;

/**
 * @author haoxr
 * @description TODO
 * @createTime 2021/3/13 11:31
 */
@Data
public class OrderDTO {

    private Long orderId;
    private Long userId;
    private Long skuId;
    private Integer skuNum;
    private Long skuPrice;

    /**
     * 是否开启事务 0-关闭 1-开启
     */
    private Boolean openTransaction;

}
