package com.waitless.benefit.coupon.infrastructure.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CouponRedisConfig {
	// @Bean
	// public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
	// 	RedisTemplate<String, Object> template = new RedisTemplate<>();
	// 	template.setConnectionFactory(connectionFactory);
	//
	// 	ObjectMapper mapper = new ObjectMapper()
	// 		.registerModule(new JavaTimeModule())
	// 		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	//
	// 	Jackson2JsonRedisSerializer<Object> serializer =
	// 		new Jackson2JsonRedisSerializer<>(mapper, Object.class);
	//
	// 	template.setKeySerializer(new StringRedisSerializer());
	// 	template.setValueSerializer(serializer);
	// 	template.setDefaultSerializer(serializer);
	//
	// 	return template;
	// }

	private final RedisProperties redisProperties;

	@Value("${spring.data.redis.port}")
	private int port;

	@Value("${spring.data.redis.password}")
	private String password;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisProperties.getHost(), port);
		redisStandaloneConfiguration.setPassword(password);
		return new LettuceConnectionFactory(
			redisStandaloneConfiguration
		);
	}

	// @Bean
	// public ObjectMapper objectMapper() {
	// 	ObjectMapper mapper = new ObjectMapper()
	// 		.findAndRegisterModules()
	// 		.enable(SerializationFeature.INDENT_OUTPUT)
	// 		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
	// 		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
	// 		.registerModules(new JavaTimeModule());
	//
	// 	// 타입 정보를 포함하도록 기본 설정 적용
	// 	mapper.activateDefaultTyping(
	// 		LaissezFaireSubTypeValidator.instance,
	// 		ObjectMapper.DefaultTyping.NON_FINAL,
	// 		JsonTypeInfo.As.PROPERTY
	// 	);
	//
	// 	return mapper;
	// }

	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		// ObjectMapper mapper = new ObjectMapper()
		// 	.registerModule(new JavaTimeModule())
		// 	.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
		// 	.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//
		// mapper.activateDefaultTyping(
		// 	BasicPolymorphicTypeValidator.builder()
		// 		.allowIfSubType(Object.class) // 허용할 클래스 직접 필터링 가능
		// 		.build(),
		// 	ObjectMapper.DefaultTyping.NON_FINAL,
		// 	JsonTypeInfo.As.PROPERTY // => "@class" 포함
		// );

		GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
			.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
			.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
			.entryTtl(Duration.ofDays(1))
			.disableCachingNullValues();
		return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory).cacheDefaults(config).build();

	}

}
