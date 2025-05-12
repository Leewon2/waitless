package com.waitless.benefit.coupon.infrastructure.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

	@Value("${spring.data.redis.host}")
	private String REDIS_HOST;

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		config.useSingleServer()
			.setAddress(String.format("redis://%s:6379", REDIS_HOST))
			.setPassword("systempass");
		return Redisson.create(config);
	}
}
