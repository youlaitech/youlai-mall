package com.youlai.gateway.handler;

import com.youlai.common.result.ResultCode;
import com.youlai.gateway.util.WebFluxUtils;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局异常处理器
 *
 * @author haoxr
 * @date 2022/10/1
 */
@Component
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable error) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(error);
        }

        if (error instanceof InvalidTokenException) {
            return WebFluxUtils.writeResponse(response, ResultCode.TOKEN_INVALID_OR_EXPIRED);
        }

        return Mono.error(error);
    }

}
