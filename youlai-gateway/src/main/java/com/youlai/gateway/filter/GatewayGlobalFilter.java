package com.youlai.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.youlai.common.constant.SecurityConstants;
import com.youlai.common.result.ResultCode;
import com.youlai.gateway.util.WebFluxUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.text.ParseException;

/**
 * 安全拦截全局过滤器，非网关鉴权的逻辑
 * <p>
 * 在ResourceServerManager#check鉴权善后一些无关紧要的事宜(线上请求拦截、黑名单拦截)
 *
 * @author haoxr
 * @date 2022/2/15
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class GatewayGlobalFilter implements GlobalFilter, Ordered {

    private final RedisTemplate redisTemplate;

    @Value("${spring.profiles.active}")
    private String env;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 拦截线上禁止的操作
        boolean isForbiddenRequest = isForbiddenRequest(request);
        if (isForbiddenRequest) {
            return WebFluxUtils.writeResponse(response, ResultCode.FORBIDDEN_OPERATION);
        }

        // 拦截黑名单的JWT
        String authorization = request.getHeaders().getFirst("Authorization");
        String payload = this.getPayload(authorization);
        if (StrUtil.isBlank(payload)) {
            return chain.filter(exchange);
        }

        String jti = JSONUtil.parseObj(payload).getStr("jti");
        Boolean isBlackJwt = redisTemplate.hasKey(SecurityConstants.TOKEN_BLACKLIST_PREFIX + jti);
        if (isBlackJwt) {
            return WebFluxUtils.writeResponse(response, ResultCode.TOKEN_ACCESS_FORBIDDEN);
        }

        // 传递 payload 给其他微服务
        request = exchange.getRequest()
                .mutate()
                .header("payload", URLEncoder.encode(payload, "UTF-8"))
                .build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }


    /**
     * 线上演示环境禁止的操作请求判断
     *
     * @param request
     * @return
     */
    private boolean isForbiddenRequest(ServerHttpRequest request) {
        String requestPath = request.getPath().pathWithinApplication().value();
        if (env.equals("prod")) {
            String method = request.getMethodValue();
            // PUT和DELETE方法禁止
            if (HttpMethod.DELETE.matches(method) || HttpMethod.PUT.matches(method)) {
                return !SecurityConstants.PERMIT_PATHS.stream().anyMatch(permitPath -> requestPath.contains(permitPath));
            } else if (HttpMethod.POST.matches(method)) {
                return SecurityConstants.FORBID_PATHS.stream().anyMatch(forbiddenPath -> requestPath.contains(forbiddenPath));
            }
        }
        return false;
    }

    /**
     * 获取JWT的载体payload
     *
     * @param authorization 请求头authorization
     * @return
     * @throws ParseException
     */
    public String getPayload(String authorization) throws ParseException {
        String payload = null;
        if (StrUtil.isNotBlank(authorization) && StrUtil.startWithIgnoreCase(authorization, "Bearer ")) {
            authorization = StrUtil.replaceIgnoreCase(authorization, "Bearer ", "");
            payload = JWSObject.parse(authorization).getPayload().toString();
        }
        return payload;
    }

}
