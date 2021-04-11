package com.youlai.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.youlai.common.result.ResultCode;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.PostConstruct;

/**
 * @author haoxr
 * @description 自定义网关流控异常响应
 * @createTime 2021/4/11 19:58
 */
@Configuration
public class SentinelConfiguration {

    @PostConstruct
    private void initBlockHandler() {
        BlockRequestHandler blockRequestHandler = (exchange, t) ->
                ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(ResultCode.FLOW_LIMITING.toString()));
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

}
