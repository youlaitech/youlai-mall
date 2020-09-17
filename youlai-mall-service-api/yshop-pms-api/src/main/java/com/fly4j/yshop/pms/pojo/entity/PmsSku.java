package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PmsSku extends BaseEntity {
  @TableId(type = IdType.ASSIGN_ID)
  private Integer id;
  private Long spu_id;
  private String specs;
  private BigDecimal price;
  private Integer stock;
  private Integer stock_locked;
  private String pic_url;
  private Integer sales_volume;
}
