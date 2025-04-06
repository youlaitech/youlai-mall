package com.youlai.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.system.model.entity.RoleMenu;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单业务接口
 *
 * @author haoxr
 * @since 2.5.0
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    List<Long> listMenuIdsByRoleId(Long roleId);


    /**
     * 刷新权限缓存(所有角色)
     */
    void refreshRolePermsCache();

    /**
     * 刷新权限缓存(指定角色)
     *
     * @param roleCode 角色编码
     */
    void refreshRolePermsCache(String roleCode);

    /**
     * 刷新权限缓存(修改角色编码时调用)
     *
     * @param oldRoleCode 旧角色编码
     * @param newRoleCode 新角色编码
     */
    void refreshRolePermsCache(String oldRoleCode, String newRoleCode);

    /**
     * 获取角色权限集合
     *
     * @param roles 角色编码集合
     * @return 权限集合
     */
    Set<String> getRolePermsByRoleCodes(Set<String> roles);
}
