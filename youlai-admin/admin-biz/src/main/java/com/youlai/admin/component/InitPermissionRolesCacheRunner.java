package com.youlai.admin.component;

import cn.hutool.core.collection.CollectionUtil;
import com.youlai.admin.pojo.SysPermission;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.common.core.constant.AuthConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 容器启动完成加载资源权限数据到缓存
 */
@Component
@AllArgsConstructor
@Slf4j
public class InitPermissionRolesCacheRunner implements CommandLineRunner {

    private RedisTemplate redisTemplate;

    private ISysPermissionService iSysPermissionService;

    @Override
    public void run(String... args) {
        log.info("InitPermissionRolesCacheRunner run");
        redisTemplate.delete(AuthConstants.PERMISSION_RULES_KEY);

        List<SysPermission> permissions = iSysPermissionService.listForPermissionRoles();
        Map<String, List<String>> permissionRules = new TreeMap<>();
        Optional.ofNullable(permissions).orElse(new ArrayList<>()).forEach(permission -> {

            // 转换 roleId -> ROLE_{roleId}
            List<String> roles = Optional.ofNullable(permission.getRoleIds())
                    .orElse(new ArrayList<>())
                    .stream()
                    .map(roleId -> AuthConstants.AUTHORITY_PREFIX + roleId)
                    .collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(roles)) {
                permissionRules.put(permission.getPermission(), roles);
            }
            redisTemplate.opsForHash().putAll(AuthConstants.PERMISSION_RULES_KEY, permissionRules);
        });
    }
}
