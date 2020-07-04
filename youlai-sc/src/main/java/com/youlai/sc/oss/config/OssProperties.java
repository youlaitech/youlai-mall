package com.youlai.sc.oss.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oss")
@Data
public class OssProperties {

    private String active;

    private Minio minio;

    @Data
    public static class Minio{

        private String endpoint;

        private String accessKey;

        private String secretKey;

        private String bucketName;

    }

}
