package com.youlai.mall.sale.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "横幅视图对象")
@Data
public class BannerVO {

    @Schema(description="广告标题")
    private String title;

    @Schema(description="横幅图片URL")
    private String imageUrl;

    @Schema(description="跳转URL")
    private String redirectUrl;

}
