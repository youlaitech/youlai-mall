package com.youlai.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.mapper.SysRoleMenuMapper;
import com.youlai.system.pojo.entity.SysRoleMenu;
import com.youlai.system.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单
 *
 * @author haoxr
 * @date 2022/10/2
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Long> listMenuIdsByRoleId(Long roleId) {
        List<Long> menuIds = this.baseMapper.listMenuIdsByRoleId(roleId);
        return menuIds;
    }


    /**
     * 获取角色权限标识集合
     *
     * @param roles
     * @return ["sys:user:add"]
     */
    @Override
    public Set<String> listPerms(Set<String> roles) {
        Set<String> perms = this.baseMapper.listPerms(roles);
        return perms;
    }

}
