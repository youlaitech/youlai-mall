package com.youlai.mall.sms.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("广告横幅对象")
@Data
public class AdBannerVO {

    @ApiModelProperty("广告标题")
    private String title;

    @ApiModelProperty("横幅图片URL")
    private String picUrl;

    @ApiModelProperty("跳转URL")
    private String redirectUrl;

}
