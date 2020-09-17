package com.youlai.admin.component;


import com.youlai.common.core.constant.AuthConstants;
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
        roleNames.add(AuthConstants.AUTHORITY_PREFIX + "1");
        resourceRoleMap.put("/youlai-admin/**", roleNames);
        redisTemplate.delete(AuthConstants.RESOURCE_ROLES_MAP_KEY);
        redisTemplate.opsForHash().putAll(AuthConstants.RESOURCE_ROLES_MAP_KEY, resourceRoleMap);
    }
}
