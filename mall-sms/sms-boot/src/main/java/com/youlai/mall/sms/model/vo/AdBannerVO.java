package com.youlai.mall.sms.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "广告横幅对象")
@Data
public class AdBannerVO {

    @Schema(description="广告标题")
    private String title;

    @Schema(description="横幅图片URL")
    private String picUrl;

    @Schema(description="跳转URL")
    private String redirectUrl;

}
