package com.youlai.mall.sms.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 广告分页视图对象
 *
 * @author haoxr
 * @date 2022/11/12
 */
@ApiModel("广告分页视图对象")
@Data
public class AdvertPageVO {

    @ApiModelProperty("广告ID")
    private Integer id;

    @ApiModelProperty("广告标题")
    private String title;

    @ApiModelProperty("广告图片")
    private String picUrl;

    @ApiModelProperty("开始时间")
    @JsonFormat( pattern = "yyyy-MM-dd")
    private Date beginTime;

    @ApiModelProperty("截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty("状态")
    private Integer status;

    private Integer sort;

    private  String redirectUrl;

    private String remark;

}
