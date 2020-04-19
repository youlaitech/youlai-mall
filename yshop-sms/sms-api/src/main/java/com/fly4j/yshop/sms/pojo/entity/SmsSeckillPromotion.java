package com.fly4j.yshop.sms.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class SmsSeckillPromotion extends BaseEntity {

  @TableId(type = IdType.ASSIGN_ID)
  private Long id;
  private String title;
  private Date start_time;
  private Date end_time;
  private Integer status;
}
