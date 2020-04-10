package com.fly4j.yshop.gms.pojo.entity;

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
  private String picUrl;
  private long sort;
  private long isDelete;

}
