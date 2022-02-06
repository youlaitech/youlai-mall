package com.youlai.common.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁 Redisson net配置
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2021/2/22
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
    private Integer database;

    @Value("${spring.redis.lettuce.pool.min-idle}")
    private Integer connectionMinimumIdleSize = 1;// 默认最小空闲连接数

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://" + host + ":" + port);
        singleServerConfig.setDatabase(database);
        singleServerConfig.setConnectionMinimumIdleSize(connectionMinimumIdleSize);
        singleServerConfig.setPassword(password);
        return Redisson.create(config);
    }

}
