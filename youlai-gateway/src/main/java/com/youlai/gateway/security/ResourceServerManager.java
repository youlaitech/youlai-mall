package com.youlai.gateway.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.youlai.common.constant.AuthConstants;
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
import java.util.Optional;
import java.util.Set;

/**
 * 网关自定义鉴权管理器
 *
 * @author haoxr
 * @date 2020-05-01
 */
@Component
@AllArgsConstructor
@Slf4j
public class ResourceServerManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private RedisTemplate redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        // 预检请求放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }
        PathMatcher pathMatcher = new AntPathMatcher(); // 【声明定义】Ant路径匹配模式，“请求路径”和缓存中权限规则的“URL权限标识”匹配
        String path = request.getURI().getPath();

        String token = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION_KEY);

        // 移动端请求无需鉴权，只需认证（即JWT的验签和是否过期判断）
        if (pathMatcher.match(GlobalConstants.APP_API_PATTERN, path)) {
            // 如果token以"bearer "为前缀，则必经过NimbusReactiveJwtDecoder#decode和JwtTimestampValidator#validate等解析和验证通过的，即表示已认证
            if (StrUtil.isNotBlank(token) && token.startsWith(AuthConstants.AUTHORIZATION_PREFIX)) {
                return Mono.just(new AuthorizationDecision(true));
            }else{
                return Mono.just(new AuthorizationDecision(false));
            }
        }

        // Restful接口权限设计 @link https://www.cnblogs.com/haoxianrui/p/14396990.html
        String restfulPath = request.getMethodValue() + ":" + path;
        log.info("请求方法:RESTFul请求路径：{}", restfulPath);

        // 缓存取【URL权限标识->角色集合】权限规则
        Map<String, Object> permRolesRules = redisTemplate.opsForHash().entries(GlobalConstants.URL_PERM_ROLES_KEY);

        // 根据 “请求路径” 和 权限规则中的“URL权限标识”进行Ant匹配，得出拥有权限的角色集合
        Set<String> hasPermissionRoles = CollectionUtil.newHashSet(); // 【声明定义】有权限的角色集合
        boolean needToCheck = false; // 【声明定义】是否需要被拦截检查的请求，如果缓存中权限规则中没有任何URL权限标识和此次请求的URL匹配，默认不需要被鉴权


        for (Map.Entry<String, Object> permRoles : permRolesRules.entrySet()) {
            String perm = permRoles.getKey(); // 缓存权限规则的键：URL权限标识
            if (pathMatcher.match(perm, restfulPath)) {
                List<String> roles = Convert.toList(String.class, permRoles.getValue()); // 缓存权限规则的值：有请求路径访问权限的角色集合
                hasPermissionRoles.addAll(Convert.toList(String.class, roles));
                needToCheck = true;
            }
        }
        // 没有设置权限规则放行；注：如果默认想拦截所有的请求请移除needToCheck变量逻辑即可，根据需求定制
        if (needToCheck == false) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 判断用户JWT中携带的角色是否有能通过权限拦截的角色
        Mono<AuthorizationDecision> authorizationDecisionMono = mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
                    log.info("用户权限（角色） : {}", authority); // ROLE_ROOT
                    String role = authority.substring(AuthConstants.AUTHORITY_PREFIX.length()); // ROOT
                    if (GlobalConstants.ROOT_ROLE_CODE.equals(role)) { // 如果是超级管理员则放行
                        return true;
                    }
                    return hasPermissionRoles.contains(role); // 用户角色中只要有一个满足则通过权限校验
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
        return authorizationDecisionMono;
    }
}
