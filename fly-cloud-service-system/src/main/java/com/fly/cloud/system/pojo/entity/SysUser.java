package com.fly.cloud.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fly.common.base.BaseEntity;
import lombok.Data;

@Data
@TableName("sys_user")
public class SysUser extends BaseEntity {

    @TableId
    private Long id;
    private String userName;
    private String nickName;
    private Integer sex;
    private String password;
    private String salt;
    private Long deptId;
    private String delFlag;
    private Integer status;
    private String avatar;
    private String tel;
    private String email;


}
