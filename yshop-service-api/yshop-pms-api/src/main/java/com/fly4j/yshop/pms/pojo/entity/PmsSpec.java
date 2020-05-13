package com.fly4j.yshop.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.yshop.common.core.entity.BaseEntity;
import lombok.Data;

@Data
public class PmsSpec extends BaseEntity {
  @TableId(type = IdType.ID_WORKER)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long spu_id;
  private String name;
  private String value;
  private String pic_url;
}
