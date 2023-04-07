package com.youlai.gateway.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Collections;
import java.util.List;


/**
 * OAuth Client Security 配置
 *
 * @author haoxr
 * @date 2022/8/28
 */
@ConfigurationProperties(prefix = "security")
@EnableWebFluxSecurity
@Slf4j
public class OAuth2ClientSecurityConfig {

    /**
     * 禁用访问路径集合
     */
    @Setter
    private List<String> forbiddenURIs;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http
    ) {
        if (CollectionUtil.isEmpty(forbiddenURIs)) {
            forbiddenURIs = Collections.EMPTY_LIST;
        }

        http.authorizeExchange()
                .pathMatchers(Convert.toStrArray(forbiddenURIs)).denyAll()
                // 放行交由资源服务器进行认证鉴权
                .anyExchange().permitAll()
                .and()
                // 禁用csrf token安全校验
                .csrf().disable();
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        CorsConfiguration corsConfig = new CorsConfiguration();

        // 允许所有请求方法
        corsConfig.addAllowedMethod("*");
        // 允许所有域，当请求头
        corsConfig.addAllowedOriginPattern("*");
        // 允许全部请求头
        corsConfig.addAllowedHeader("*");
        // 允许携带 Authorization 头
        corsConfig.setAllowCredentials(true);
        // 允许全部请求路径
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }

}