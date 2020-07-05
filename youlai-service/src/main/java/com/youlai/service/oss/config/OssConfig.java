package com.youlai.service.oss.config;

import com.youlai.service.oss.strategy.MinioStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfig {

    @Autowired
    private OssProperties ossProperties;

    @Bean
    public MinioStrategy minioStrategy() {
        MinioStrategy minioStrategy = new MinioStrategy(ossProperties.getMinio());
        return minioStrategy;
    }
}
