package com.youlai.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.form.RolePermsForm;
import com.youlai.admin.pojo.entity.SysRolePermission;

import java.util.List;

/**
 * 角色权限接口
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {


    /**
     * 根据菜单ID和角色ID获取权限ID集合
     *
     * @param menuId
     * @param roleId
     * @return
     */
    List<Long> listPermIds(Long menuId, Long roleId);


    /**
     * 保存角色的权限
     *
     * @return
     */
    boolean saveRolePerms(RolePermsForm rolePermsForm);


}
