package com.youlai.laboratory.spring.aop;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 说明描述
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/26 0026 0:24
 */
@Configuration
public class MyDefaultAdvisorAutoProxyCreator {

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();

    }
}
