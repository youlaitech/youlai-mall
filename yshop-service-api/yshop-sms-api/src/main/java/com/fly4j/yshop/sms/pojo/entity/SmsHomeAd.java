package com.fly4j.yshop.sms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fly4j.common.core.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class SmsHomeAd extends BaseEntity {
  @TableId(type=IdType.ASSIGN_ID)
 private String id;
 private String name;
 private String pic_url;
 private Date start_time;
 private Date end_time;
 private Integer status;
 private Integer sort;
 private String link_url;
 private String remark;
}
