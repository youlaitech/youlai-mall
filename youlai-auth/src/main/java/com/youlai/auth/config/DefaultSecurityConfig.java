package com.youlai.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {

    /**
     * Spring Security 安全过滤器链配置
     *
     * @param http 安全配置
     * @return
     */
    @Bean
    @Order(0)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requestMatcherRegistry ->
                        requestMatcherRegistry.anyRequest().authenticated()
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
                // 白名单②: 不走过滤器链(场景：静态资源js、css、html)
                web.ignoring().requestMatchers(
                        "/webjars/**",
                        "/doc.html",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**"
                );
    }


}
