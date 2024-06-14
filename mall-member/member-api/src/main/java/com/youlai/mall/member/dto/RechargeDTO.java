package com.youlai.mall.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema 
@Data
public class RechargeDTO {

    @Schema(description = "充值金额")
    private String price;

    @Schema(description = "充值说明")
    private String name;

    @Schema(description = "用来通知指定地址")
    private String callbackurl;

    @Schema(description = "跳转地址")
    private String reurl;

    @Schema(description = "用户存放您的用户ID")
    private String thirduid;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "其他信息")
    private String other;


}
