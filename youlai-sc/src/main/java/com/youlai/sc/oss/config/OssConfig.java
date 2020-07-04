package com.youlai.sc.oss.config;

import com.youlai.sc.oss.core.OssService;
import com.youlai.sc.oss.strategy.MinioStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfig {

    @Autowired
    private OssProperties ossProperties;

    @Bean
    public OssService ossService() {
        OssService ossService = new OssService();
        String active = this.ossProperties.getActive();


        ossService.setOssStrategy(minioStrategy());
        return ossService;
    }

    @Bean
    public MinioStrategy minioStrategy() {
        MinioStrategy minioStrategy = new MinioStrategy();
        minioStrategy.setConfig(ossProperties.getMinio());
        return minioStrategy;
    }
}
