package com.youlai.auth.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.youlai.auth.extension.mobile.SmsCodeAuthenticationProvider;
import com.youlai.auth.extension.wechat.WechatAuthenticationProvider;
import com.youlai.mall.ums.api.MemberFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@ConfigurationProperties(prefix = "security")
@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService sysUserDetailsService;
    private final UserDetailsService memberUserDetailsService;
    private final WxMaService wxMaService;
    private final MemberFeignClient memberFeignClient;
    private final StringRedisTemplate redisTemplate;

    @Setter
    private List<String> ignoreUrls;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (CollectionUtil.isEmpty(ignoreUrls)) {
            ignoreUrls = Arrays.asList("/webjars/**", "/doc.html", "/swagger-resources/**", "/v2/api-docs");
        }

        log.info("whitelist path:{}", JSONUtil.toJsonStr(ignoreUrls));

        http
                .authorizeRequests()
                .antMatchers(Convert.toStrArray(ignoreUrls)).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    /**
     * 认证管理对象
     *
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(wechatAuthenticationProvider()).
                authenticationProvider(daoAuthenticationProvider()).
                authenticationProvider(smsCodeAuthenticationProvider());
    }

    /**
     * 手机验证码认证授权提供者
     *
     * @return
     */
    @Bean
    public SmsCodeAuthenticationProvider smsCodeAuthenticationProvider() {
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
        provider.setUserDetailsService(memberUserDetailsService);
        provider.setRedisTemplate(redisTemplate);
        return provider;
    }

    /**
     * 微信认证授权提供者
     *
     * @return
     */
    @Bean
    public WechatAuthenticationProvider wechatAuthenticationProvider() {
        WechatAuthenticationProvider provider = new WechatAuthenticationProvider();
        provider.setUserDetailsService(memberUserDetailsService);
        provider.setWxMaService(wxMaService);
        provider.setMemberFeignClient(memberFeignClient);
        return provider;
    }


    /**
     * 用户名密码认证授权提供者
     *
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(sysUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setHideUserNotFoundExceptions(false); // 是否隐藏用户不存在异常，默认:true-隐藏；false-抛出异常；
        return provider;
    }


    /**
     * 密码编码器
     * <p>
     * 委托方式，根据密码的前缀选择对应的encoder，例如：{bcypt}前缀->标识BCYPT算法加密；{noop}->标识不使用任何加密即明文的方式
     * 密码判读 DaoAuthenticationProvider#additionalAuthenticationChecks
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
