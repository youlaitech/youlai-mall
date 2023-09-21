package com.youlai.gateway.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import java.util.Optional;

/**
 * Swagger 处理器
 *
 * @author haoxr
 * @since 2023/9/21
 */
@Component
@RequiredArgsConstructor
public class SwaggerHandler {

    private final SwaggerResourcesProvider swaggerResources;

    @Autowired(required = false)
    private SecurityConfiguration securityConfiguration;

    @Autowired(required = false)
    private UiConfiguration uiConfiguration;


    /**
     * 聚合各个服务的 swagger 接口
     */
    public Mono<ServerResponse> handleResource(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters
                        .fromValue(swaggerResources.get()));
    }

    /**
     * 权限处理器
     */
    public Mono<ServerResponse> handleSecurity(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters
                        .fromValue(Optional.ofNullable(securityConfiguration)
                                .orElse(SecurityConfigurationBuilder.builder().build())));
    }

    /**
     * UI处理器
     */
    public Mono<ServerResponse> handleUi(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters
                        .fromValue(Optional.ofNullable(uiConfiguration)
                                .orElse(UiConfigurationBuilder.builder().build())));
    }
}
