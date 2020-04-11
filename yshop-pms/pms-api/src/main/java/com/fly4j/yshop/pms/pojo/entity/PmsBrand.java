package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

@Data
public class PmsBrand extends BaseEntity {
  @TableId
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private long id;
  private String name;
  private String description;
  private String pic_url;
  private long sort;

}
