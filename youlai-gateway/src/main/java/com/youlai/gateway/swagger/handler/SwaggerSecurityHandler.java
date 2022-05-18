package com.youlai.gateway.swagger.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.Optional;


/**
 * 权限处理器
 *
 * @author haoxr
 * @date 2022/5/17
 */
@Component
public class SwaggerSecurityHandler implements HandlerFunction<ServerResponse> {

    @Autowired(required = false)
    private SecurityConfiguration securityConfiguration;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        Mono<ServerResponse> responseMono = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters
                        .fromValue(Optional.ofNullable(securityConfiguration)
                                .orElse(SecurityConfigurationBuilder.builder().build())));
        return responseMono;
    }
}
