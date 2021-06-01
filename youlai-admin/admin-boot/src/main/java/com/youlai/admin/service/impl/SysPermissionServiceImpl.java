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
    public List<SysPermission> listPermRoles() {
        return this.baseMapper.listPermRoles();
    }

    @Override
    public IPage<SysPermission> list(Page<SysPermission> page, SysPermission permission) {
        List<SysPermission> list = this.baseMapper.list(page, permission);
        page.setRecords(list);
        return page;
    }

    @Override
    public boolean refreshPermRolesCache() {
        redisTemplate.delete(AuthConstants.PERMISSION_ROLES_KEY);
        List<SysPermission> permissions = this.listPermRoles();
        if (CollectionUtil.isNotEmpty(permissions)) {
            // 初始化URL【权限->角色(集合)】规则
            List<SysPermission> urlPermList = permissions.stream()
                    .filter(item -> StrUtil.isNotBlank(item.getUrlPerm()))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(urlPermList)) {
                Map<String, List<String>> urlPermRoles = new HashMap<>();
                urlPermList.stream().forEach(item -> {
                    String perm = item.getUrlPerm();
                    List<String> roles = item.getRoles();
                    roles.add(SystemConstants.ROOT_ROLE_CODE);
                    urlPermRoles.put(perm, roles);
                });
                redisTemplate.opsForHash().putAll(SystemConstants.URL_PERM_ROLES_KEY, urlPermRoles);
            }
            // 初始化URL【按钮->角色(集合)】规则
            List<SysPermission> btnPermList = permissions.stream()
                    .filter(item -> StrUtil.isNotBlank(item.getUrlPerm()))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(btnPermList)) {
                Map<String, List<String>> btnPermRoles = new HashMap<>();
                btnPermList.stream().forEach(item -> {
                    String perm = item.getUrlPerm();
                    List<String> roles = item.getRoles();
                    roles.add(SystemConstants.ROOT_ROLE_CODE);
                    btnPermRoles.put(perm, roles);
                });
                redisTemplate.opsForHash().putAll(SystemConstants.BTN_PERM_ROLES_KEY, btnPermRoles);
            }
        }
        return true;
    }


    @Override
    public List<String> listPermByRoles(List<String> roles) {
        return this.baseMapper.listPermByRoles(roles);
    }
}
