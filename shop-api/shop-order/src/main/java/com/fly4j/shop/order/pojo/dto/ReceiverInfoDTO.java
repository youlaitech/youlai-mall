package com.fly4j.shop.order.pojo.dto;

import lombok.Data;

/**
 * @description:订单修改收货人信息参数
 * @author: Mr.
 * @create: 2020-03-26 16:28
 **/
@Data
public class ReceiverInfoDTO {
    private Long orderId;
    private String receiverName;
    private String receiverPhone;
    private String receiverPostCode;
    private String receiverDetailAddress;
    private String receiverProvince;
    private String receiverCity;
    private String receiverRegion;
    private Integer status;
}
