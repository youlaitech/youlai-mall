package com.youlai.service.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "oss.minio")
public class MinIOProperties {

    private String endpoint;

    private String accessKey;

    private String secretKey;

}
