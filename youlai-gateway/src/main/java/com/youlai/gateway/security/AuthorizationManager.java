package com.youlai.gateway.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.youlai.common.constant.GlobalConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.youlai.common.constant.AuthConstants.AUTHORITY_PREFIX;

/**
 * 网关自定义鉴权管理器
 */
@Component
@AllArgsConstructor
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private RedisTemplate redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {

        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        // Restful接口权限设计 @link https://www.cnblogs.com/haoxianrui/p/14396990.html
        String restPath = request.getMethodValue() + "_" + request.getURI().getPath();
        PathMatcher pathMatcher = new AntPathMatcher();
        // 对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }
        // 从redis取【权限->角色(集合)】规则
        Map<String, Object> permRolesRule = redisTemplate.opsForHash().entries(GlobalConstants.URL_PERM_ROLES_KEY);
        Set<String> hasPermRoles = CollectionUtil.newHashSet(); // 有权限的角色集合
        boolean needCheck = false; // 是否被设置需要鉴权
        for (Map.Entry<String, Object> permRoles : permRolesRule.entrySet()) {
            String perm = permRoles.getKey(); // URL权限标识
            if (pathMatcher.match(perm, restPath)) {
                List<String> roles = Convert.toList(String.class, permRoles.getValue());
                hasPermRoles.addAll(Convert.toList(String.class, roles));
                needCheck = true;
            }
        }
        // 如果没有设置权限拦截则放行
        if (needCheck == false) {
            return Mono.just(new AuthorizationDecision(true));
        }
        // 判断用户JWT中携带的角色是否有能通过权限拦截的角色
        Mono<AuthorizationDecision> authorizationDecisionMono = mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
                    log.info("authority : {}", authority); // ROLE_ROOT
                    String role = authority.substring(AUTHORITY_PREFIX.length()); // ROOT
                    if (GlobalConstants.ROOT_ROLE_CODE.equals(role)) { // 如果是超级管理员则放行
                        return true;
                    }
                    return hasPermRoles.contains(role); // 用户角色中只要有一个满足则通过权限校验
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
        return authorizationDecisionMono;
    }
}
