package com.fly4j.shop.order.pojo.dto;

import lombok.Data;

/**
 * @description:
 * @author: Mr.
 * @create: 2020-03-26 16:28
 **/
@Data
public class OrderDeliveryDTO {

    private Long orderId;
    private String deliveryCompany;
    private String deliveryNo;
}
