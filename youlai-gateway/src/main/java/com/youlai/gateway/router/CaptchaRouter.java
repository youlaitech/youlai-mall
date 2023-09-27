package com.youlai.gateway.router;

import com.youlai.gateway.handler.CaptchaHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 验证码路由
 *
 * @author haoxr
 * @since 2.4.1
 */
@Configuration
@RequiredArgsConstructor
public class CaptchaRouter {

    private final CaptchaHandler captchaHandler;

    @Bean
    public RouterFunction<ServerResponse> captchaRouterFunction() {
        return RouterFunctions
                .route(RequestPredicates.GET("/captcha")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), captchaHandler);
    }


}
