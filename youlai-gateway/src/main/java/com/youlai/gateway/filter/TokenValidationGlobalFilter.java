package com.youlai.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTPayload;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.youlai.common.constant.RedisConstants;
import com.youlai.common.result.ResultCode;
import com.youlai.gateway.util.WebFluxUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Map;

/**
 * Token 验证全局过滤器
 *
 * @author Ray Hao
 * @since 3.1.0
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TokenValidationGlobalFilter implements GlobalFilter, Ordered {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String BEARER_PREFIX = "Bearer ";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlank(authorization) || !StrUtil.startWithIgnoreCase(authorization, BEARER_PREFIX)) {
            return chain.filter(exchange);
        }

        try {
            String token = authorization.substring(BEARER_PREFIX.length());
            JWSObject jwsObject = JWSObject.parse(token);
            String jti = (String) jwsObject.getPayload().toJSONObject().get(JWTPayload.JWT_ID);
            Boolean isBlackToken = redisTemplate.hasKey(RedisConstants.TOKEN_BLACKLIST_PREFIX + jti);
            if (Boolean.TRUE.equals(isBlackToken)) {
                return WebFluxUtils.writeErrorResponse(response, ResultCode.TOKEN_ACCESS_FORBIDDEN);
            }
        } catch (ParseException e) {
            log.error("Parsing token failed in TokenValidationGlobalFilter", e);
            return WebFluxUtils.writeErrorResponse(response, ResultCode.TOKEN_INVALID);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }


}
