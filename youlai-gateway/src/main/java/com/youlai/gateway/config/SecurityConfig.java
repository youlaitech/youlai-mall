package com.youlai.gateway.config;

import cn.hutool.core.collection.CollectionUtil;
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
 * 客户端配置
 *
 * @author haoxr
 * @since 2022/8/28
 */
@ConfigurationProperties(prefix = "security")
@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    /**
     * 黑名单请求路径列表
     */
    @Setter
    private List<String> blacklistPaths;


    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchange ->
                        {
                            if (CollectionUtil.isNotEmpty(blacklistPaths)) {
                                exchange.pathMatchers(Convert.toStrArray(blacklistPaths)).authenticated();
                            }
                            exchange.anyExchange().permitAll();
                        }
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }




}