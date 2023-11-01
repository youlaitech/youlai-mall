package com.youlai.auth.config;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
import java.util.List;

/**
 * 默认安全配置
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {

    /**
     * Spring Security 安全过滤器链配置
     *
     * @param http 安全配置
     * @return 安全过滤器链
     */
    @Bean
    @Order(0)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults());

        return http.build();
    }


    /**
     * Spring Security 自定义安全配置
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) ->
                // 白名单: 不走过滤器链(场景：静态资源js、css、html)
                web.ignoring().requestMatchers(
                        convertToAntPathRequestMatchers(
                                Arrays.asList("/webjars/**",
                                        "/doc.html",
                                        "/swagger-resources/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**")
                        ));
    }


    /**
     * 将路径列表转换为AntPathRequestMatcher数组
     *
     * @param paths 路径列表
     * @return AntPathRequestMatcher数组
     */
    public static AntPathRequestMatcher[] convertToAntPathRequestMatchers(List<String> paths) {
        if (CollectionUtil.isEmpty(paths)) {
            return new AntPathRequestMatcher[0];
        }

        return paths.stream()
                .map(AntPathRequestMatcher::new)
                .toArray(AntPathRequestMatcher[]::new);
    }


}
