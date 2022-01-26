package com.youlai.common.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/10/13 22:44
 */
@ConfigurationProperties(prefix = "aliyun.sms")
@Configuration
@Data
public class AliyunSmsProperties {

    private String accessKeyId;

    private String accessKeySecret;

    private String domain;

    private String regionId;

    private String templateCode;

    private String signName;

}
