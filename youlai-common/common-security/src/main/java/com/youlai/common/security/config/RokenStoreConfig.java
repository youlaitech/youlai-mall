package com.youlai.common.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class RokenStoreConfig {

    @Bean
    public RedisTokenStore redisTokenStore(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTokenStore redisTokenStore = new RedisTokenStore(lettuceConnectionFactory);
        redisTokenStore.setPrefix("token:");
        return redisTokenStore;
    }
}
