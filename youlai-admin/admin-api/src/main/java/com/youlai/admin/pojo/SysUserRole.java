package com.youlai.admin.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysUserRole {

    private Long userId;

    private Long roleId;

}
