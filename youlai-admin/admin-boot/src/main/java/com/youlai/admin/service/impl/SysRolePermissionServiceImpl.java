package com.youlai.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.mapper.SysRolePermissionMapper;
import com.youlai.admin.pojo.dto.RolePermissionDTO;
import com.youlai.admin.pojo.entity.SysRolePermission;
import com.youlai.admin.service.ISysRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {


    @Override
    public List<Long> listPermissionId(Long roleId) {
        return this.baseMapper.listPermissionId(null, roleId);
    }

    @Override
    public List<Long> listPermissionId(Long menuId, Long roleId) {
        return this.baseMapper.listPermissionId(menuId, roleId);
    }

    @Override
    public boolean update(RolePermissionDTO rolePermission) {
        boolean result = true;
        List<Long> permissionIds = rolePermission.getPermissionIds();
        Long menuId = rolePermission.getMenuId();
        Long roleId = rolePermission.getRoleId();
        List<Long> dbPermissionIds = this.baseMapper.listPermissionId(menuId, roleId);

        // 删除数据库存在此次提交不存在的
        if (CollectionUtil.isNotEmpty(dbPermissionIds)) {
            List<Long> removePermissionIds = dbPermissionIds.stream().filter(id -> !permissionIds.contains(id)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(removePermissionIds)) {
                this.remove(new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, roleId)
                        .in(SysRolePermission::getPermissionId, removePermissionIds));
            }
        }

        // 插入数据库不存在的
        if (CollectionUtil.isNotEmpty(permissionIds)) {
            List<Long> insertPermissionIds = permissionIds.stream().filter(id -> !dbPermissionIds.contains(id)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(insertPermissionIds)) {
                List<SysRolePermission> roleMenus = new ArrayList<>();
                for (Long insertPermissionId : insertPermissionIds) {
                    SysRolePermission sysRolePermission = new SysRolePermission().setRoleId(roleId).setPermissionId(insertPermissionId);
                    roleMenus.add(sysRolePermission);
                }
                result = this.saveBatch(roleMenus);
            }
        }
        return result;
    }


}
