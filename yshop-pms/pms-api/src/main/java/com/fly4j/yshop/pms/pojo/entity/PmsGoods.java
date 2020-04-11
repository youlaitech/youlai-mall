package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class PmsGoods extends BaseEntity {

  @TableId
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @ApiModelProperty(value = "主键id",hidden = true)
  private long id;

  @ApiModelProperty(value = "商品名称")
  private String name;

  @ApiModelProperty(value = "商品编号")
  private String goodsSn;

  @ApiModelProperty(value = "分类ID")
  private long categoryId;

  @ApiModelProperty(value = "品牌ID")
  private long brandId;

  @ApiModelProperty(value = "关键词")
  private String keywords;
  private String subtitle;
  private String description;
  private String picUrl;
  private String album;
  private String unit;
  private double marketPrice;
  private double retailPrice;
  private long sort;
  private String detail;
  private long isNew;
  private long isHot;
  private long isSale;
  private long isDelete;
}
