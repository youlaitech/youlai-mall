package com.youlai.mall.sale.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;


/**
 * 广告分页视图对象
 *
 * @author Ray.Hao
 * @since 2022/11/12
 */
@Schema(description = "广告分页视图对象")
@Data
public class AdvertPageVO {

    @Schema(description="广告ID")
    private Integer id;

    @Schema(description="广告标题")
    private String title;

    @Schema(description="广告图片")
    private String imgUrl;

    @Schema(description="开始时间")
    @JsonFormat( pattern = "yyyy-MM-dd")
    private Date beginTime;

    @Schema(description="截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @Schema(description="状态")
    private Integer status;

    private Integer sort;

    private  String redirectUrl;

    private String remark;

}
