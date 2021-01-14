package com.youlai.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.pojo.SysRole;
import com.youlai.admin.pojo.SysRoleMenu;
import com.youlai.admin.pojo.SysRolePermission;
import com.youlai.admin.pojo.SysUserRole;
import com.youlai.admin.mapper.SysRoleMapper;
import com.youlai.admin.service.ISysRoleMenuService;
import com.youlai.admin.service.ISysRolePermissionService;
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
    private ISysRolePermissionService iSysRolePermissionService;

    @Override
    public boolean add(SysRole role) {
        List<Long> menuIds = role.getMenuIds();
        this.save(role);
        Long roleId = role.getId();
        List<SysRoleMenu> roleMenus = new ArrayList<>();
        Optional.ofNullable(menuIds).ifPresent(list -> list.stream().forEach(menuId ->
                roleMenus.add(new SysRoleMenu().setRoleId(roleId).setMenuId(menuId)))
        );
        boolean result = iSysRoleMenuService.saveBatch(roleMenus);
        return result;
    }


    @Override
    public boolean update(SysRole role) {
        Long roleId = role.getId();
        List<Long> menuIds = role.getMenuIds();
        List<Long> dbMenuIds = iSysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, roleId)).stream()
                .map(item -> item.getMenuId()).collect(Collectors.toList()); // 数据库角色拥有菜单权限ID

        // 删除角色菜单
        Optional.ofNullable(dbMenuIds).filter(item -> menuIds == null || !menuIds.contains(item))
                .ifPresent(list -> list.stream()
                        .forEach(menuId -> iSysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>()
                                .eq(SysRoleMenu::getRoleId, roleId)
                                .eq(SysRoleMenu::getMenuId, menuId)
                        )));

        // 新增角色菜单
        Optional.ofNullable(menuIds).filter(item -> dbMenuIds == null || !dbMenuIds.contains(item))
                .ifPresent(list -> list.stream()
                        .forEach(menuId -> {
                            iSysRoleMenuService.save(new SysRoleMenu().setRoleId(roleId).setMenuId(menuId));
                        }));
        return true;
    }

    @Override
    public boolean delete(List<Long> ids) {
        Optional.ofNullable(ids).orElse(new ArrayList<>()).forEach(id -> {
            int count = iSysUserRoleService.count(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, id));
            if (count > 0) {
                throw new BizException("该角色已分配用户，无法删除");
            }
            iSysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));
        });

        return this.removeByIds(ids);
    }

    @Override
    public boolean update(Long roleId, List<Long> permissionIds) {
        List<Long> dbPermissionIds = iSysRolePermissionService.list(
                new LambdaQueryWrapper<SysRolePermission>()
                        .eq(SysRolePermission::getRoleId, roleId)).stream().map(item -> item.getPermissionId()).collect(Collectors.toList());

        // 删除角色资源
        Optional.ofNullable(dbPermissionIds)
                .filter(item -> permissionIds == null || !permissionIds.contains(item))
                .ifPresent(list -> list.stream().forEach(permissionId ->
                        iSysRolePermissionService.remove(new LambdaQueryWrapper<SysRolePermission>()
                                .eq(SysRolePermission::getRoleId, roleId)
                                .eq(SysRolePermission::getPermissionId, permissionId)))
                );

        // 新增角色资源
        Optional.ofNullable(permissionIds)
                .filter(item -> dbPermissionIds == null || !dbPermissionIds.contains(item))
                .ifPresent(list -> list.stream().forEach(permissionId -> {
                    iSysRolePermissionService.save(new SysRolePermission().setRoleId(roleId).setPermissionId(permissionId));
                }));
        return true;
    }
}
