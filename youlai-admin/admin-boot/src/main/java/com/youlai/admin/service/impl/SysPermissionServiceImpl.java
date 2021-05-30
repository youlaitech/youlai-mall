package com.youlai.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.common.constant.SystemConstants;
import com.youlai.admin.mapper.SysPermissionMapper;
import com.youlai.admin.pojo.entity.SysPermission;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.common.constant.AuthConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SysPermission> listPermissionRoles() {
        return this.baseMapper.listPermRoles();
    }

    @Override
    public IPage<SysPermission> list(Page<SysPermission> page, SysPermission permission) {
        List<SysPermission> list = this.baseMapper.list(page, permission);
        page.setRecords(list);
        return page;
    }

    @Override
    public boolean refreshPermissionRolesCache() {
        redisTemplate.delete(AuthConstants.PERMISSION_ROLES_KEY);
        List<SysPermission> permissions = this.listPermissionRoles();
        Map<String, List<String>> permissionRoles = new TreeMap<>();
        Optional.ofNullable(permissions).orElse(new ArrayList<>()).forEach(permission -> {
            // 转换 roleId -> ROLE_{roleId}
            List<String> roles = Optional.ofNullable(permission.getRoles())
                    .orElse(new ArrayList<>())
                    .stream()
                    .map(roleId -> AuthConstants.AUTHORITY_PREFIX + roleId)
                    .collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(roles)&& StrUtil.isNotBlank(permission.getUrlPerm())) {
                permissionRoles.put(permission.getUrlPerm(), roles);
            }
            redisTemplate.opsForHash().putAll(AuthConstants.PERMISSION_ROLES_KEY, permissionRoles);
        });
        return true;
    }

 /*   private boolean initUrlPermissionRoles() {
        redisTemplate.delete(SystemConstants.URL_PERM_ROLES_PREFIX + "*");
        List<SysPermission> permissions = this.listPermissionRoles(1);
        Map<Integer, List<SysPermission>> map = permissions.stream().collect(Collectors.groupingBy(SysPermission::getTenantId));
        Iterator<Map.Entry<Integer, List<SysPermission>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, List<SysPermission>> entry = iterator.next();
            Integer tenantId = entry.getKey();
            List<SysPermission> perms = entry.getValue();
            Map<String, List<String>> permRoles = new TreeMap<>();
            Optional.ofNullable(perms).orElse(new ArrayList<>()).forEach(perm -> {
                // 转换 roleId -> ROLE_{角色编码}
                List<String> roles = Optional.ofNullable(perm.getRoles())
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(role -> AuthConstants.AUTHORITY_PREFIX + role)
                        .collect(Collectors.toList());
                permRoles.put(perm.getMethod() + "_" + perm.getPerm(), roles);
                redisTemplate.opsForHash().putAll(AuthConstants.URL_PERM_ROLES_PREFIX + tenantId, permRoles);
            });
        }
        return true;
    }

    private boolean initBtnPermissionRoles() {
        redisTemplate.delete(AuthConstants.BTN_PERM_ROLES_PREFIX + "*");
        List<SysPermission> permissions = this.listPermissionRoles(2);
        Map<Integer, List<SysPermission>> map = permissions.stream().collect(Collectors.groupingBy(SysPermission::getTenantId));
        Iterator<Map.Entry<Integer, List<SysPermission>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, List<SysPermission>> entry = iterator.next();
            Integer tenantId = entry.getKey();
            List<SysPermission> perms = entry.getValue();
            Map<String, List<String>> permRoles = new TreeMap<>();
            Optional.ofNullable(perms).orElse(new ArrayList<>()).forEach(perm -> {
                // 转换 roleId -> ROLE_{角色编码}
                List<String> roles = Optional.ofNullable(perm.getRoles())
                        .orElse(new ArrayList<>())
                        .stream()
                        .collect(Collectors.toList());
                permRoles.put(perm.getPerm(), roles);
                redisTemplate.opsForHash().putAll(AuthConstants.BTN_PERM_ROLES_PREFIX + tenantId, permRoles);
            });
        }
        return true;
    }*/



    @Override
    public List<String> listPermsByRoleIds(List<Long> roleIds, Integer type) {
        return this.baseMapper.listPermsByRoleIds(roleIds, type);
    }
}
