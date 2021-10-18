package com.youlai.common.redis.redisson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huawei
 * @desc redisson 连接配置类
 * @email huawei_code@163.com
 * @date 2021/2/22
 */
@Component
@ConfigurationProperties(prefix = "redisson")
@Data
public class RedissonProperties {

    private String serverAddress;

    private String port;

    private String password;

}
