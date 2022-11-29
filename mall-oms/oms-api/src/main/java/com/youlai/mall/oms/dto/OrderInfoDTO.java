package com.youlai.mall.oms.dto;

import lombok.Data;

/**
 * 订单传输层对象
 *
 * @author haoxr
 * @date 2022/4/17
 */
@Data
public class OrderInfoDTO {

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 订单状态
     */
    private Integer status;

}
