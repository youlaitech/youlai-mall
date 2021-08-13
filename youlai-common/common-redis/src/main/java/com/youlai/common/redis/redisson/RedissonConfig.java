package com.youlai.common.redis.redisson;

import cn.hutool.core.util.StrUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(properties.getServerAddress() + ":" + properties.getPort());
        singleServerConfig.setDatabase(properties.getDatabase());
        String password = properties.getPassword();
        if (StrUtil.isNotBlank(password)) {
            singleServerConfig.setPassword(password);
        }
        return Redisson.create(config);
    }


}
