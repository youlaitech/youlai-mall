package com.fly.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysUser extends BaseEntity {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
    private String userName;
    private String nickName;
    private Integer sex;
    private String password;
    private String salt;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;
    private String delFlag;
    private Integer status;
    private String avatarUrl;
    private String tel;
    private String email;
}
