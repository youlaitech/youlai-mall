package com.youlai.mall.oms.dto;

import lombok.Data;

/**
 * 订单信息传输层对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/17 21:12
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
