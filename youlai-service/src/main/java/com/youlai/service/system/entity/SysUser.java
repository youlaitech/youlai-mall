package com.youlai.service.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.entity.BaseEntity;
import lombok.Data;


@Data
public class SysUser extends BaseEntity {

    @TableId
    private Long id;

    private String username;

    private String nickname;

    private Integer gender;

    private String password;

    private String salt;

    private Integer deptId;

    private Integer deleted;

}
