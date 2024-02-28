package com.youlai.common.sms.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 阿里云短信配置
 *
 * @author haoxr
 * @since 2021/10/13
 */
@Configuration
@ConfigurationProperties(prefix = "sms.aliyun")
@Data
public class AliyunSmsProperties {

    /**
     * 阿里云账户的Access Key ID，用于API请求认证
     */
    private String accessKeyId;

    /**
     *阿里云账户的Access Key Secret，用于API请求认证
     */
    private String accessKeySecret;

    /**
     * 阿里云短信服务API的域名 eg: dysmsapi.aliyuncs.com
     */
    private String domain;

    /**
     * 阿里云服务的区域ID，如cn-shanghai
     */
    private String regionId;

    /**
     * 短信签名，必须是已经在阿里云短信服务中注册并通过审核的
     */
    private String signName;

    /**
     * 模板编码
     */
    private Map<String, String> templateCodes;
}
