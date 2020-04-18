package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel
@Data
public class PmsSpu extends BaseEntity {

    @TableId(type = IdType.ID_WORKER)
    @ApiModelProperty(value = "主键id", hidden = true)
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "分类ID")
    private Long category_id;

    @ApiModelProperty(value = "品牌ID")
    private Long brand_id;

    @ApiModelProperty(value = "关键词")
    private String keywords;
    private String subtitle;
    private String description;
    @ApiModelProperty(value = "商品显示图")
    private String pic_url;
    @ApiModelProperty(value = "商品图册")
    private String pic_urls;
    private String unit;
    private BigDecimal price;
    private Long sort;
    private String detail;
    private Integer is_new;
    private Integer is_hot;

    private Integer status;
}
