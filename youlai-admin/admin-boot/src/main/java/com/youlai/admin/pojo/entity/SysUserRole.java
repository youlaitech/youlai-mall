package com.youlai.admin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class SysUserRole {

    private Long userId;

    private Long roleId;

}
