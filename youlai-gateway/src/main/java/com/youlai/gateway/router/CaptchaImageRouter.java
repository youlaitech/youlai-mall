package com.youlai.gateway.router;

import com.youlai.gateway.kaptcha.handler.CaptchaImageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 图片验证码路由
 *
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2021/10/4
 */
@Configuration
public class CaptchaImageRouter {

    @Bean
    public RouterFunction<ServerResponse> routeFunction(CaptchaImageHandler captchaImageHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/validate-code")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), captchaImageHandler::handle);
    }

}
