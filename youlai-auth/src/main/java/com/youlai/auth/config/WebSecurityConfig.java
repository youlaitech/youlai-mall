package com.youlai.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 登录失败处理handler，返回一段json
        http
                .formLogin().failureHandler(
                (req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    Map<String, Object> map = new HashMap<>();
                    map.put("status", 401);
                    if (e instanceof LockedException) {
                        map.put("msg", "账户被锁定，登录失败！");
                    } else if (e instanceof BadCredentialsException) {
                        map.put("msg", "用户名或密码输入错误，登录失败！");
                    } else if (e instanceof DisabledException) {
                        map.put("msg", "账户被禁用，登录失败！");
                    } else if (e instanceof AccountExpiredException) {
                        map.put("msg", "账户过期，登录失败！");
                    } else if (e instanceof CredentialsContainer) {
                        map.put("msg", "密码过期，登录失败");
                    } else {
                        map.put("msg", "登录失败！");
                    }
                    out.write(new ObjectMapper().writeValueAsString(map));
                    out.flush();
                    out.close();
                }


        )
                .and()
                .authorizeRequests().requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .and()
                .authorizeRequests().antMatchers("/oauth/public_key", "/oauth/logout").permitAll().anyRequest().authenticated()
                .and()
                .csrf().disable();

    }

    /**
     * 如果不配置SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, e) -> {

      /*
      if (!user.isEnabled()) {
                throw new DisabledException("该账户已被禁用!");
            } else if (!user.isAccountNonLocked()) {
                throw new LockedException("该账号已被锁定!");
            } else if (!user.isAccountNonExpired()) {
                throw new AccountExpiredException("该账号已过期!");
            } else if (!user.isCredentialsNonExpired()) {
                throw new CredentialsExpiredException("该账户的登录凭证已过期，请重新登录!");
            }
*/
            if (e instanceof DisabledException) {
                log.info(e.getMessage());
            } else if (e instanceof LockedException) {
                log.info(e.getMessage());
            } else if (e instanceof AccountExpiredException) {
                log.info(e.getMessage());
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
