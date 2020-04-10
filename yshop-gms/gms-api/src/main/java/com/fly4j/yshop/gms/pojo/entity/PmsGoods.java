package com.fly4j.yshop.gms.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

@Data
public class PmsGoods extends BaseEntity {

  @TableId
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private long id;
  private String name;
  private String goodsSn;
  private long categoryId;
  private long brandId;
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
