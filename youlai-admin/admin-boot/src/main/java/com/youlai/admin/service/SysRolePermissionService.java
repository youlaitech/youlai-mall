package com.youlai.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.entity.SysRolePermission;

import java.util.List;

/**
 * 角色权限业务接口
 *
 * @author haoxr
 * @date 2022/6/4
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 获取角色拥有的权限ID集合
     *
     * @param roleId
     * @return
     */
    List<Long> listPermIdsByRoleId(Long roleId);
}
