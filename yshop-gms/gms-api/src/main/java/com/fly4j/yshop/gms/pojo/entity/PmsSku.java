package com.fly4j.yshop.gms.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

@Data
public class PmsSku extends BaseEntity {
  @TableId
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private long id;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private long goodsId;
  private String specs;
  private double price;
  private long number;
  private String picUrl;
  private long salesVolume;
  private long isDelete;

}
