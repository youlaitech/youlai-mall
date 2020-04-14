package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class PmsSpu extends BaseEntity {

    @TableId(type = IdType.ID_WORKER)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "主键id", hidden = true)
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "分类ID")
    private long category_id;

    @ApiModelProperty(value = "品牌ID")
    private long brand_id;

    @ApiModelProperty(value = "关键词")
    private String keywords;
    private String subtitle;
    private String description;
    private String pic_url;
    private String album;
    private String unit;
    private double market_price;
    private double retail_price;
    private long sort;
    private String detail;
    private long is_new;
    private long is_hot;
}
