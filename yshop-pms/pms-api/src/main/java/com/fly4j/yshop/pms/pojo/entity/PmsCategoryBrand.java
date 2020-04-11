package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class PmsCategoryBrand {
  @TableId
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private long category_id;

  @TableId
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private long brand_id;

}
