package com.fly4j.yshop.ums.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import lombok.Data;

@Data
public class UmsMember extends BaseEntity {
  @TableId(type = IdType.ID_WORKER)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;
  private Long levelId;
  private String username;
  private String password;
  private String nickname;
  private String mobile;
  private String avatar;
  private Integer gender;
  private Integer status;
}
