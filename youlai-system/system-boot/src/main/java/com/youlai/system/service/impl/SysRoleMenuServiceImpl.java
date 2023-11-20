package com.youlai.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.mapper.SysRoleMenuMapper;
import com.youlai.system.model.entity.SysRoleMenu;
import com.youlai.system.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色菜单业务实现类
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    @Override
    public List<Long> listMenuIdsByRoleId(Long roleId) {
        return this.baseMapper.listMenuIdsByRoleId(roleId);
    }

}
