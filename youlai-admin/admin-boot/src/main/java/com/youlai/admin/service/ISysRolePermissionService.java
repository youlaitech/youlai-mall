package com.youlai.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.dto.RolePermissionDTO;
import com.youlai.admin.pojo.entity.SysRolePermission;

import java.util.List;

public interface ISysRolePermissionService extends IService<SysRolePermission> {

    List<Long> listPermissionIds(Long moduleId,Long roleId, Integer type);
    List<Long> listPermissionIds(Long roleId, Integer type);
    boolean update(RolePermissionDTO rolePermission);


}
