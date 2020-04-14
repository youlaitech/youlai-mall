package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

@Data
public class PmsSku extends BaseEntity {
  @TableId(type = IdType.ID_WORKER)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long spu_id;
  private String specs;
  private double price;
  private Long number;
  private String pic_url;
  private Long sales_volume;

}
