package com.youlai.auth.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Setter
    private List<String> ignoreUrls;

    /**
     * Spring Security 安全过滤器链配置
     *
     * @param http 安全配置
     * @return
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requestMatcherRegistry ->
                        {
                            if (CollectionUtil.isNotEmpty(ignoreUrls)) {
                                // 白名单①：走 springSecurityFilterChain 过滤器链(场景：验证码登录走验证码过滤器)
                                requestMatcherRegistry.requestMatchers(Convert.toStrArray(ignoreUrls)).permitAll();
                            }
                            requestMatcherRegistry.anyRequest().authenticated();
                        }
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(withDefaults())
        ;
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
