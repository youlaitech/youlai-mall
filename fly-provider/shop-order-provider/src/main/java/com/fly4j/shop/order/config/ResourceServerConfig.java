package com.fly4j.shop.order.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Slf4j
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))

                .and().requestMatchers().antMatchers("/**")

                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/**/*.css",
                        "/**/*.js",
                        "/webjars/**",
                        "/swagger/**",
                        "/v2/api-docs",
                        "/doc.html",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/system/**",
                        "/brands/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS,"/oauth/token").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .httpBasic();
    }
}