package com.youlai.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.system.pojo.entity.SysRoleMenu;

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
     * 获取权限标识集合
     *
     * @param roles 角色code集合
     * @return ["sys:user:add"]
     */
    List<String> listPerms(List<String> roles);
}
