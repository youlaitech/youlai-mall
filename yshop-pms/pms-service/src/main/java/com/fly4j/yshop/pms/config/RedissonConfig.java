package com.fly4j.yshop.pms.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

	@Bean
	public RedissonClient redissonClient() {
		// 默认连接地址 127.0.0.1:6379
		 RedissonClient client = Redisson.create();
		 return client;

		// redis不在本机
//		Config config = new Config();
//		config.useSingleServer().setAddress("redis://localhost:6379");
//		return Redisson.create(config);
	}
}