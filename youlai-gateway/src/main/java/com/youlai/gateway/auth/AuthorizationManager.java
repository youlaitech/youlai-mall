package com.youlai.gateway.auth;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.youlai.admin.api.dto.UserDTO;
import com.youlai.common.core.constant.AuthConstants;
import com.youlai.gateway.config.WhiteUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WhiteUrlsConfig whiteUrlsConfig;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        URI uri = request.getURI();
        //白名单路径直接放行
        PathMatcher pathMatcher = new AntPathMatcher();
        List<String> whiteUrls = whiteUrlsConfig.getUrls();
        for (String ignoreUrl : whiteUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }
        //对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        try {
            String token = request.getHeaders().getFirst(AuthConstants.JWT_TOKEN_HEADER);
            if (StrUtil.isBlank(token)) {
                return Mono.just(new AuthorizationDecision(false));
            }
            token = token.replace(AuthConstants.JWT_TOKEN_PREFIX, "");
            JWSObject jwsObject = JWSObject.parse(token);
            String payload = jwsObject.getPayload().toString(); // jwt 载体部分
            UserDTO userDTO = JSONUtil.toBean(payload, UserDTO.class);

            if (AuthConstants.ADMIN_CLIENT_ID.equals(userDTO.getClientId())
                    && !pathMatcher.match(AuthConstants.ADMIN_URL_PATTERN, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(false));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return Mono.just(new AuthorizationDecision(false));
        }

        // 非管理端路径直接放行
        if (!pathMatcher.match(AuthConstants.ADMIN_URL_PATTERN, uri.getPath())) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 管理端路径需要校验权限
        Map<Object, Object> resourceRolesMap = redisTemplate.opsForHash().entries(AuthConstants.RESOURCE_ROLES_MAP_KEY);
        Iterator<Object> iterator = resourceRolesMap.keySet().iterator();

        // 拥有该请求资源权限的角色ID集合
        List<String> authorities = new ArrayList<>();
        while (iterator.hasNext()) {
            String pattern = (String) iterator.next();
            if (pathMatcher.match(pattern, uri.getPath())) {
                authorities.addAll(Convert.toList(String.class, resourceRolesMap.get(pattern)));
            }
        }
        Mono<AuthorizationDecision> authorizationDecisionMono = mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(roleId -> authorities.contains(roleId)) // 判断用户角色中是否有访问资源权限的角色，一个就好
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));

        return authorizationDecisionMono;
    }
}
