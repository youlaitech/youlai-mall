package com.fly4j.yshop.sms.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class SmsSeckillPromotion extends BaseEntity {

  @TableId(type = IdType.ID_WORKER)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;
  private String title;
  private Date start_time;
  private Date end_time;
  private Long status;
}
