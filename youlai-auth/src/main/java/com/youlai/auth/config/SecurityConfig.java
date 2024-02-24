package com.youlai.auth.config;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

/**
 * 默认安全配置
 */
@ConfigurationProperties(prefix = "security")
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 白名单路径列表
     */
    @Setter
    private List<String> whitelistPaths;

    /**
     * Spring Security 安全过滤器链配置
     *
     * @param http 安全配置
     * @return 安全过滤器链
     */
    @Bean
    @Order(0)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http.authorizeHttpRequests((requests) ->
                        {
                            if (CollectionUtil.isNotEmpty(whitelistPaths)) {
                                for (String whitelistPath : whitelistPaths) {
                                    requests.requestMatchers(mvcMatcherBuilder.pattern(whitelistPath)).permitAll();
                                }
                            }
                            requests.anyRequest().authenticated();
                        }
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

}
