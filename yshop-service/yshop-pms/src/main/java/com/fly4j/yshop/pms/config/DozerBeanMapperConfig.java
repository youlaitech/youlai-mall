package com.fly4j.yshop.pms.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实体转换工具
 * @author haoxianrui
 * @since 2020-04-21
 **/

@Configuration
public class DozerBeanMapperConfig {
    @Bean
    public DozerBeanMapper dozerBeanMapper(){
        DozerBeanMapper dozerBeanMapper=new DozerBeanMapper();
        return dozerBeanMapper;
    }

}
