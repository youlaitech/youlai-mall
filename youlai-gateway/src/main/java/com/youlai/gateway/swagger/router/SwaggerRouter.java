package com.youlai.gateway.swagger.router;

import com.youlai.gateway.swagger.handler.SwaggerResourceHandler;
import com.youlai.gateway.swagger.handler.SwaggerSecurityHandler;
import com.youlai.gateway.swagger.handler.SwaggerUiHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Swagger路由
 *
 * @author haoxr
 * @date 2022/5/16 16:34
 */
@Configuration
@RequiredArgsConstructor
public class SwaggerRouter {

    /**
     * 聚合各个服务的swagger接口
     */
    private final SwaggerResourceHandler swaggerResourceHandler;
    /**
     * 权限处理器
     */
    private final SwaggerSecurityHandler swaggerSecurityHandler;
    /**
     * UI处理器
     */
    private final SwaggerUiHandler swaggerUiHandler;

    @Bean
    public RouterFunction<ServerResponse> swaggerRouterFunction() {
        return RouterFunctions
                .route(RequestPredicates.GET("/swagger-resources/configuration/security").and(RequestPredicates.accept(MediaType.ALL)), swaggerSecurityHandler::handle)
                .andRoute(RequestPredicates.GET("/swagger-resources/configuration/ui").and(RequestPredicates.accept(MediaType.ALL)), swaggerUiHandler::handle)
                .andRoute(RequestPredicates.GET("/swagger-resources").and(RequestPredicates.accept(MediaType.ALL)), swaggerResourceHandler::handle);
    }

}
