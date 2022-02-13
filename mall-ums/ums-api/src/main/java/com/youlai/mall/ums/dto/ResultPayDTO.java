package com.youlai.mall.ums.dto;

import lombok.Data;

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
