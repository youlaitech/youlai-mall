package com.youlai.mall.auth.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("sys_user")
@Accessors(chain = true)
public class SysUser extends BaseEntity {

    @TableId
    private Long userId;
    private String userName;
    private String nickName;
    private Integer sex;
    private String password;
    private String salt;
    private Long deptId;
    private String delFlag;
    private Integer status;
    private String avatarUrl;
    private String tel;
    private String email;


}
