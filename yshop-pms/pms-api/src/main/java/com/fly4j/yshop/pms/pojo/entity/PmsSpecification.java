package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

@Data
public class PmsSpecification extends BaseEntity {
  @TableId(type = IdType.ID_WORKER)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private long id;
  private long goods_Id;
  private String name;
  private String value;
  private String pic_url;
}
