package com.youlai.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.entity.BaseEntity;
import lombok.Data;


@Data
public class SysUser extends BaseEntity {

    @TableId
    private Integer id;

    private String username;

    private String nickname;

    private Integer gender;

    private String password;

    private String status;

    private String salt;

    private Integer deptId;

    private Integer deleted;

}
