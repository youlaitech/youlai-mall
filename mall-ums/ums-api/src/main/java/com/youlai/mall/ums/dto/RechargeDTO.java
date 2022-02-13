package com.youlai.mall.ums.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel
@Data
public class RechargeDTO {

    @ApiModelProperty(value = "充值金额")
    private String price;

    @ApiModelProperty(value = "充值说明")
    private String name;

    @ApiModelProperty(value = "用来通知指定地址")
    private String callbackurl;

    @ApiModelProperty(value = "跳转地址")
    private String reurl;

    @ApiModelProperty(value = "用户存放您的用户ID")
    private String thirduid;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "其他信息")
    private String other;


}
