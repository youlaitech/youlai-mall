package com.youlai.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.youlai.common.result.ResultCode;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;


/**
 * 自定义网关流控异常
 *
 * @author haoxr
 * @since 2022/7/24
 */
@Configuration
public class SentinelConfig {

    @PostConstruct
    private void initBlockHandler() {
        GatewayCallbackManager.setBlockHandler(
                (exchange, t) ->
                        ServerResponse
                                .status(HttpStatus.TOO_MANY_REQUESTS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(
                                        BodyInserters.fromValue(ResultCode.FLOW_LIMITING.toString())
                                )
        );
    }
}
