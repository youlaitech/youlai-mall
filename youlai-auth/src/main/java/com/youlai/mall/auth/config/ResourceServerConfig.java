package com.youlai.mall.auth.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
            .headers()
            .frameOptions().disable()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/user/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .csrf().disable();
    }
}