package com.youlai.system.pojo.entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SysRolePermission {
    private Long roleId;
    private Long permissionId;
}
