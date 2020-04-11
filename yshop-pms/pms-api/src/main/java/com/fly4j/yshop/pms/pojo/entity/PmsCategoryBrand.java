package com.fly4j.yshop.gms.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class PmsCategoryBrand {
  @TableId
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private long categoryId;

  @TableId
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private long brandId;

}
