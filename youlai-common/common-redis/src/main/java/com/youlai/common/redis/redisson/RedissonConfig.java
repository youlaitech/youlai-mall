package com.youlai.common.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author huawei
 * @desc Redisson 配置
 * @email huawei_code@163.com
 * @date 2021/2/22
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient(RedissonProperties properties) {
        if (properties.getServerAddress() == null) {
            return null;
        }
        Config config = new Config();
        config.useSingleServer()
                //可以用"rediss://"来启用SSL连接
                .setAddress(properties.getServerAddress() + ":" + properties.getPort())
                .setDatabase(properties.getDatabase())
                .setPassword(properties.getPassword());
        return Redisson.create(config);
    }


}
