package com.youlai.mall.oms.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 配置
 * <p>
 * Spring Doc FAQ: https://springdoc.org/#faq
 *
 * @author haoxr
 * @since 2023/2/17
 */
@Configuration
public class SwaggerConfig {

    /**
     * 认证服务地址
     */
    @Value("${knife4j.oauth2.token-url}")
    private String tokenUrl ;

    /**
     * 接口信息
     */
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("Authorization",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .flows(new OAuthFlows()
                                                .password(
                                                        new io.swagger.v3.oas.models.security.OAuthFlow()
                                                                .tokenUrl(tokenUrl)
                                                                .refreshUrl(tokenUrl)
                                                                .scopes(
                                                                        new Scopes()
                                                                                .addString("all", "全部权限")
                                                                )
                                                )
                                        )
                                        .scheme("bearer")
                                        .in(SecurityScheme.In.HEADER)
                                        .bearerFormat("JWT")
                        )
                )
                .info(new Info()
                        .title("订单服务")
                        .version("3.0.0")
                        .description("订单、购物车等接口")
                        .license(new License().name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                );
    }


}
