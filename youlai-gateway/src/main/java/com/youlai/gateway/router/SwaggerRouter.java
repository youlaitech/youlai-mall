package com.youlai.gateway.router;

import com.youlai.gateway.handler.SwaggerHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;

/**
 * Swagger 路由
 *
 * @author haoxr
 * @since  2022/5/16
 */
@Configuration
@RequiredArgsConstructor
public class SwaggerRouter {

    private final SwaggerHandler swaggerHandler;

    @Bean
    public RouterFunction<ServerResponse> swaggerRouterFunction() {
        return RouterFunctions
                .route(RequestPredicates.GET("/swagger-resources/configuration/security").and(RequestPredicates.accept(MediaType.ALL)), swaggerHandler::handleSecurity)
                .andRoute(RequestPredicates.GET("/swagger-resources/configuration/ui").and(RequestPredicates.accept(MediaType.ALL)), swaggerHandler::handleUi)
                .andRoute(RequestPredicates.GET("/swagger-resources").and(RequestPredicates.accept(MediaType.ALL)), swaggerHandler::handleResource);
    }

}
