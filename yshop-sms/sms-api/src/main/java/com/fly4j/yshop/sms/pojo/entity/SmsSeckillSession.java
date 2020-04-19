package com.fly4j.yshop.sms.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

@Data
public class SmsSeckillSession extends BaseEntity {
  
  @TableId(type = IdType.ASSIGN_ID)
  private Long id;
  private String name;
  private String start_time;
  private String end_time;
  private Integer status;
}
