package com.fly4j.yshop.ums.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fly4j.common.core.entity.BaseEntity;
import lombok.Data;

@Data
public class UmsUser extends BaseEntity {

  @TableId(type = IdType.ASSIGN_ID)
  private Long id;
  private Integer level;
  private String username;
  private String password;
  private String nickname;
  private String mobile;
  private String avatar;
  private Integer gender;
  private Integer status;
}
