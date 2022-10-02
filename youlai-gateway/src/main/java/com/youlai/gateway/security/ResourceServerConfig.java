package com.youlai.gateway.security;

import cn.hutool.core.convert.Convert;
import com.youlai.common.result.ResultCode;
import com.youlai.gateway.util.WebFluxUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资源服务器配置
 *
 * @author haoxr
 * @date 2020/05/01
 */
@ConfigurationProperties(prefix = "security")
@Configuration
@EnableWebFluxSecurity
@Slf4j
public class ResourceServerConfig {

    @Resource
    private  ResourceServerAuthenticationManager resourceServerAuthenticationManager;

    @Resource
    private  ResourceServerAuthorizationManager resourceServerAuthorizationManager;

    @Setter
    private List<String> ignoreUrls;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        if (ignoreUrls == null) {
            log.error("网关白名单路径读取失败：Nacos配置读取失败，请检查配置中心连接是否正确！");
        }

        //认证过滤器
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(resourceServerAuthenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(new ServerBearerTokenAuthenticationConverter());

        http.authorizeExchange()
                .pathMatchers(Convert.toStrArray(ignoreUrls)).permitAll()
                .anyExchange().access(resourceServerAuthorizationManager)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler()) // 处理未授权
                .authenticationEntryPoint(authenticationEntryPoint()) //处理未认证
                .and().csrf().disable()
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        ;

        return http.build();
    }

    /**
     * 自定义未授权响应
     */
    @Bean
    ServerAccessDeniedHandler accessDeniedHandler() {
        return (exchange, denied) -> {
            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
                    .flatMap(response -> WebFluxUtils.writeResponse(response, ResultCode.ACCESS_UNAUTHORIZED));
            return mono;
        };
    }

    /**
     * token无效或者已过期自定义响应
     */
    @Bean
    ServerAuthenticationEntryPoint authenticationEntryPoint() {
        return (exchange, e) -> {
            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
                    .flatMap(response -> WebFluxUtils.writeResponse(response, ResultCode.TOKEN_INVALID_OR_EXPIRED));
            return mono;
        };
    }



}
