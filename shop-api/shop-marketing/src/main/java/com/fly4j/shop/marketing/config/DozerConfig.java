package com.fly4j.shop.marketing.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实体相互转换工具
 */
@Configuration
public class DozerConfig {

    @Bean
    public DozerBeanMapper dozerBeanMapper(){
        DozerBeanMapper mapper=new DozerBeanMapper();
        return mapper;
    }

}
