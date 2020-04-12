package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

@Data
public class PmsCategory extends BaseEntity {
  @TableId
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;
  private String name;
  private String description;
  private Long parent_id;
  private Integer level;
  private String icon_url;
  private String pic_Url;
  private Long sort;


}
