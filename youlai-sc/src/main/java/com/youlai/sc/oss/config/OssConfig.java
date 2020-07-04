package com.youlai.sc.oss.config;

import com.youlai.sc.oss.strategy.MinioStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

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
