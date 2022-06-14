package com.youlai.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.youlai.common.constant.SecurityConstants;
import com.youlai.common.result.ResultCode;
import com.youlai.gateway.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;

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
public class GatewaySecurityFilter implements GlobalFilter, Ordered {

    private final RedisTemplate redisTemplate;

    @Value("${spring.profiles.active}")
    private String env;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 线上环境请求拦截处理，实际请自行删除下面代码块
        {
            String requestPath = request.getPath().pathWithinApplication().value();
            if (env.equals("prod")) {
                String methodValue = request.getMethodValue();
                if (SecurityConstants.PROD_FORBID_METHODS.contains(methodValue)) { // PUT和DELETE方法禁止
                    // 是否需要放行的请求路径
                    boolean isPermitPath = SecurityConstants.PROD_PERMIT_PATHS.stream().anyMatch(permitPath ->requestPath.contains(permitPath));
                    if (!isPermitPath) {
                        return ResponseUtils.writeErrorInfo(response, ResultCode.FORBIDDEN_OPERATION);
                    }
                } else if(methodValue.equals("POST")){
                    // 是否禁止放行的请求路径
                    boolean isForbidPath = SecurityConstants.PROD_FORBID_PATHS.stream().anyMatch(permitPath -> requestPath.equals(permitPath));
                    if (isForbidPath) {
                        return ResponseUtils.writeErrorInfo(response, ResultCode.FORBIDDEN_OPERATION);
                    }
                }
            }
        }

        // 非JWT放行不做后续解析处理
        String token = request.getHeaders().getFirst(SecurityConstants.AUTHORIZATION_KEY);
        if (StrUtil.isBlank(token) || !StrUtil.startWithIgnoreCase(token, SecurityConstants.JWT_PREFIX)) {
            return chain.filter(exchange);
        }

        // 解析JWT获取jti，以jti为key判断redis的黑名单列表是否存在，存在则拦截访问
        token = StrUtil.replaceIgnoreCase(token, SecurityConstants.JWT_PREFIX, Strings.EMPTY);
        String payload = StrUtil.toString(JWSObject.parse(token).getPayload());
        JSONObject jsonObject = JSONUtil.parseObj(payload);
        String jti = jsonObject.getStr(SecurityConstants.JWT_JTI);
        Boolean isBlack = redisTemplate.hasKey(SecurityConstants.TOKEN_BLACKLIST_PREFIX + jti);
        if (isBlack) {
            return ResponseUtils.writeErrorInfo(response, ResultCode.TOKEN_ACCESS_FORBIDDEN);
        }

        // 存在token且不在黑名单中，request写入JWT的载体信息传递给微服务
        request = exchange.getRequest().mutate()
                .header(SecurityConstants.JWT_PAYLOAD_KEY, URLEncoder.encode(payload, "UTF-8"))
                .build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
