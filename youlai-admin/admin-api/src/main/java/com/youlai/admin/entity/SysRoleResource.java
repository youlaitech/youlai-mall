package com.youlai.admin.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysRoleResource {
    private Integer roleId;
    private Integer resourceId;
}
