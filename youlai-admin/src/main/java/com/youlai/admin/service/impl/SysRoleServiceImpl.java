package com.youlai.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.domain.entity.SysRole;
import com.youlai.admin.domain.entity.SysRoleMenu;
import com.youlai.admin.domain.entity.SysUserRole;
import com.youlai.admin.mapper.SysRoleMapper;
import com.youlai.admin.service.ISysRoleMenuService;
import com.youlai.admin.service.ISysRoleService;
import com.youlai.admin.service.ISysUserRoleService;
import com.youlai.common.web.exception.BizException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private ISysRoleMenuService iSysRoleMenuService;
    private ISysUserRoleService iSysUserRoleService;

    @Override
    public boolean add(SysRole role) {
        List<Integer> menuIds = role.getMenuIds();
        List<SysRoleMenu> roleMenus = new ArrayList<>();
        Optional.ofNullable(menuIds)
                .orElse(new ArrayList<>())
                .forEach(menuId -> {
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setRoleId(role.getId());
                    roleMenu.setMenuId(menuId);
                    roleMenus.add(roleMenu);
                });
        iSysRoleMenuService.saveBatch(roleMenus);
        return this.save(role);
    }

    @Override
    public boolean update(SysRole role) {
        List<Integer> menuIds = iSysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, role.getId()))
                .stream()
                .map(item -> item.getMenuId())
                .collect(Collectors.toList()); // 数据库角色拥有菜单权限ID

        List<Integer> updatedMenuIds = role.getMenuIds(); // 修改后的角色拥有菜单权限ID

        // 删除的角色菜单ID集合
        List<Integer> removeMenuIds = Optional.ofNullable(menuIds)
                .orElse(new ArrayList<>())
                .stream()
                .filter(menuId -> !Optional.ofNullable(updatedMenuIds).orElse(new ArrayList<>()).contains(menuId))
                .collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(removeMenuIds)) {
            iSysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getMenuId,removeMenuIds));
        }

        // 新增的角色菜单ID集合
        List<Integer> addMenuIds = Optional.ofNullable(updatedMenuIds)
                .orElse(new ArrayList<>())
                .stream()
                .filter(menuId -> !Optional.ofNullable(menuIds).orElse(new ArrayList<>()).contains(menuId))
                .collect(Collectors.toList());

        List<SysRoleMenu> roleMenus = new ArrayList<>();
        Optional.ofNullable(addMenuIds)
                .orElse(new ArrayList<>())
                .forEach(menuId -> {
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setRoleId(role.getId());
                    roleMenu.setMenuId(menuId);
                    roleMenus.add(roleMenu);
                });
        iSysRoleMenuService.saveBatch(roleMenus); // 修改角色菜单
        return this.updateById(role);
    }

    @Override
    public boolean delete(List<Integer> ids) {
        Optional.ofNullable(ids).orElse(new ArrayList<>()).forEach(id -> {
            int count = iSysUserRoleService.count(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, id));
            if (count > 0) {
                throw new BizException("该角色已分配用户，无法删除");
            }
            // 删除角色菜单
            iSysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));
        });

        return this.removeByIds(ids);

    }


}
