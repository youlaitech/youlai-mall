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
    public List<Long> listPermissionIds(Long roleId, Integer type) {
        return this.baseMapper.listPermissionIds(null, roleId, type);
    }

    @Override
    public List<Long> listPermissionIds(Long moduleId, Long roleId, Integer type) {
        return this.baseMapper.listPermissionIds(moduleId, roleId, type);
    }

    @Override
    public boolean update(RolePermissionDTO rolePermission) {
        boolean result = true;
        List<Long> permissionIds = rolePermission.getPermissionIds();
        Long moduleId = rolePermission.getModuleId();
        Long roleId = rolePermission.getRoleId();
        Integer type = rolePermission.getType();
        List<Long> dbPermissionIds = this.baseMapper.listPermissionIds(moduleId, roleId, type);

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
