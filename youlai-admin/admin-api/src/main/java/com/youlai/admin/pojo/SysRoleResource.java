package com.youlai.admin.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysRoleResource {
    private Long roleId;
    private Long resourceId;
}
