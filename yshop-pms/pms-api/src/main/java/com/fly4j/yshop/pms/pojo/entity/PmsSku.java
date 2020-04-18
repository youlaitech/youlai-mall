package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PmsSku extends BaseEntity {
  @TableId(type = IdType.ID_WORKER)
  private Long id;
  private Long spu_id;
  private String specs;
  private BigDecimal price;
  private Integer quantity;
  private String pic_url;
  private Integer sales_volume;

}
