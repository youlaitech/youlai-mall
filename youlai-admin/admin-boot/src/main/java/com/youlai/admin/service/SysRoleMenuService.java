package com.youlai.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.entity.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuService extends IService<SysRoleMenu> {


    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId
     * @return
     */
    List<Long> listMenuIdsByRoleId(Long roleId);

    /**
     * 修改角色菜单
     * @param roleId
     * @param menuIds
     * @return
     */
    boolean update(Long roleId, List<Long> menuIds);
}
