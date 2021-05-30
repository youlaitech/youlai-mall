package com.youlai.gateway.security;

import cn.hutool.core.convert.Convert;
import com.youlai.common.constant.AuthConstants;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        String requestPermPattern = request.getMethodValue() + "_" + request.getURI().getPath();
        PathMatcher pathMatcher = new AntPathMatcher();
        // 对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }
        // 从redis取【权限->角色(集合)】规则
        Map<String, Object> cachePermRolesMap = redisTemplate.opsForHash().entries(AuthConstants.PERMISSION_ROLES_KEY);
        Set<String> havePermissionRoles = new HashSet<>(); // 拥有权限的角色
        boolean isIntercepted = false; //是否设置权限拦截
        for (Map.Entry<String, Object> permRoles : cachePermRolesMap.entrySet()) {
            String cachePermPattern = permRoles.getKey(); // URL权限标识
            if (pathMatcher.match(cachePermPattern, requestPermPattern)) {
                havePermissionRoles.addAll(Convert.toList(String.class, permRoles.getValue()));
                isIntercepted = isIntercepted == false ? true : false;
            }
        }
        // 如果没有设置权限拦截放行
        if (isIntercepted == false) {
            return Mono.just(new AuthorizationDecision(true));
        }
        Mono<AuthorizationDecision> authorizationDecisionMono = mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(role -> havePermissionRoles.contains(role))
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
        return authorizationDecisionMono;
    }
}
