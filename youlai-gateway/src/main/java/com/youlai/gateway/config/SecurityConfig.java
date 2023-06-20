package com.youlai.gateway.config;

import cn.hutool.core.convert.Convert;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;


/**
 * Security 安全配置
 *
 * @author haoxr
 * @since 2022/8/28
 */
@Configuration
@EnableWebFluxSecurity
@Slf4j
@ConfigurationProperties(prefix = "security")
public class SecurityConfig {

    @Setter
    private List<String> forbiddenURIs;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity  http) {
        http
                .authorizeExchange(exchangeSpec ->
                        exchangeSpec
                                .pathMatchers(Convert.toStrArray(forbiddenURIs)).denyAll()
                                .anyExchange().permitAll()
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }


}