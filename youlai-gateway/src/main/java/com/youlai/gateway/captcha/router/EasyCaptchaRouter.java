package com.youlai.gateway.captcha.router;

import com.youlai.gateway.captcha.handler.EasyCaptchaHandler;
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
 * @date 2022/5/17 12:10
 */
@Configuration
public class EasyCaptchaRouter {

    @Bean
    public RouterFunction<ServerResponse> captchaRouterFunction(EasyCaptchaHandler easyCaptchaHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/captcha")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), easyCaptchaHandler::handle);
    }

}
