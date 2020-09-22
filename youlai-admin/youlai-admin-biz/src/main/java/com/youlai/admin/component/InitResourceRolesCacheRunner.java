package com.youlai.admin.component;


import cn.hutool.core.collection.CollectionUtil;
import com.youlai.admin.api.entity.SysResource;
import com.youlai.admin.service.ISysResourceService;
import com.youlai.common.core.constant.AuthConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 容器启动完成初始化资源权限至Redis缓存
 */
@Component
@AllArgsConstructor
@Slf4j
public class InitResourceRolesCacheRunner implements CommandLineRunner {

    private RedisTemplate redisTemplate;

    private ISysResourceService iSysResourceService;

    @Override
    public void run(String... args) {
        log.info("InitResourceRolesCacheRunner run");
        redisTemplate.delete(AuthConstants.RESOURCE_ROLES_KEY);

        List<SysResource> resources = iSysResourceService.listForResourceRoles();
        Map<String, List<String>> resourceRolesMap = new TreeMap<>();
        Optional.ofNullable(resources).orElse(new ArrayList<>()).forEach(resource -> {

            List<String> roles = Optional.ofNullable(resource.getRoleIds()).orElse(new ArrayList<>())
                    .stream().map(roleId -> AuthConstants.AUTHORITY_PREFIX + roleId)
                    .collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(roles)) {
                resourceRolesMap.put(resource.getUrl(), roles);
            }

            redisTemplate.opsForHash().putAll(AuthConstants.RESOURCE_ROLES_KEY, resourceRolesMap);
        });
    }
}
