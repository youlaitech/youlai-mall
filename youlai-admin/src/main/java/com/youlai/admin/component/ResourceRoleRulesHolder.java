package com.youlai.admin.component;


import com.youlai.common.auth.constant.AuthConstant;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
@AllArgsConstructor
public class ResourceRoleRulesHolder {

    private RedisTemplate redisTemplate;

    @PostConstruct
    public void initResourceRolesMap() {
        Map<String, List<String>> resourceRoleMap = new TreeMap<>();
        List<String> roleNames = new ArrayList<>();
        roleNames.add(AuthConstant.AUTHORITY_PREFIX + "1_root");
        resourceRoleMap.put("/admin/users", roleNames);
        redisTemplate.delete(AuthConstant.RESOURCE_ROLES_MAP_KEY);
        redisTemplate.opsForHash().putAll(AuthConstant.RESOURCE_ROLES_MAP_KEY, resourceRoleMap);
    }
}
