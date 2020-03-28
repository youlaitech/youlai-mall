package com.fly4j.shop.order.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:修改订单费用信息参数
 * @author: Mr.
 * @create: 2020-03-26 16:28
 **/
@Data
public class MoneyInfoDTO {
    private Long orderId;
    private BigDecimal freightAmount;
    private BigDecimal discountAmount;
    private Integer status;
}
