package com.youlai.mall.oms.pojo.dto;

import lombok.Data;

/**
 * 支付结果回调接收类
 */
@Data
public class ResultPayDTO {

    private String code;
    private String msg;
    private String otherinfo;
    private String orderId;
    private String price;
    private String name;
    private String thirduid;
    private String paytype;
    private String remarks;
    private String originalprice;
}
