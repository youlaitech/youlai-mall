package com.youlai.gateway.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.gateway.component.UrlPermRolesLocalCache;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 网关自定义鉴权管理器
 *
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ResourceServerManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final RedisTemplate redisTemplate;

    // 本地缓存
    private final UrlPermRolesLocalCache urlPermRolesLocalCache;

    // 是否演示环境
    @Value("${local-cache.enabled}")
    private Boolean localCacheEnabled;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        // 预检请求放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }
        PathMatcher pathMatcher = new AntPathMatcher(); // Ant匹配器
        String method = request.getMethodValue();
        String path = request.getURI().getPath();
        String restfulPath = method + ":" + path; // Restful接口权限设计 @link https://www.cnblogs.com/haoxianrui/p/14961707.html


        // 移动端请求需认证但无需鉴权判断
        String token = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION_KEY);
        if (pathMatcher.match(GlobalConstants.APP_API_PATTERN, path)) {
            // 如果token以"bearer "为前缀，到这里说明JWT有效即已认证
            if (StrUtil.isNotBlank(token)
                    && token.startsWith(AuthConstants.AUTHORIZATION_PREFIX)) {
                return Mono.just(new AuthorizationDecision(true));
            } else {
                return Mono.just(new AuthorizationDecision(false));
            }
        }

        // 缓存取 URL权限-角色集合 规则数据
        // urlPermRolesRules = [{'key':'GET:/api/v1/users/*','value':['ADMIN','TEST']},...]
        Map<String, Object> urlPermRolesRules;
        if (localCacheEnabled) {
            urlPermRolesRules = (Map<String, Object>) urlPermRolesLocalCache.getCache(GlobalConstants.URL_PERM_ROLES_KEY);
            if (null == urlPermRolesRules) {
                urlPermRolesRules = redisTemplate.opsForHash().entries(GlobalConstants.URL_PERM_ROLES_KEY);
                urlPermRolesLocalCache.setLocalCache(GlobalConstants.URL_PERM_ROLES_KEY, urlPermRolesRules);
            }
        } else {
            urlPermRolesRules = redisTemplate.opsForHash().entries(GlobalConstants.URL_PERM_ROLES_KEY);
        }


        // 根据请求路径判断有访问权限的角色列表
        List<String> authorizedRoles = new ArrayList<>(); // 拥有访问权限的角色
        boolean requireCheck = false; // 是否需要鉴权，默认“没有设置权限规则”不用鉴权

        for (Map.Entry<String, Object> permRoles : urlPermRolesRules.entrySet()) {
            String perm = permRoles.getKey();
            if (pathMatcher.match(perm, restfulPath)) {
                List<String> roles = Convert.toList(String.class, permRoles.getValue());
                authorizedRoles.addAll(Convert.toList(String.class, roles));
                if (requireCheck == false) {
                    requireCheck = true;
                }
            }
        }
        if (requireCheck == false) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 判断JWT中携带的用户角色是否有权限访问
        Mono<AuthorizationDecision> authorizationDecisionMono = mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
                    String roleCode = authority.substring(AuthConstants.AUTHORITY_PREFIX.length()); // 用户的角色
                    if (GlobalConstants.ROOT_ROLE_CODE.equals(roleCode)) {
                        return true; // 如果是超级管理员则放行
                    }
                    boolean hasAuthorized = CollectionUtil.isNotEmpty(authorizedRoles) && authorizedRoles.contains(roleCode);
                    return hasAuthorized;
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
        return authorizationDecisionMono;
    }
}
