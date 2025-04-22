package com.waitless.benefit.coupon.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class CouponKafkaConfig {

	// @Bean
	// public ConsumerFactory<String, String> consumerFactory() {
	// 	Map<String, Object> configProps = new HashMap<>();
	// 	configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	// 	configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	// 	configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	// 	return new DefaultKafkaConsumerFactory<>(configProps);
	// }
	//
	// @Bean
	// public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
	// 	ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
	// 	factory.setConsumerFactory(consumerFactory());
	// 	return factory;
	// }
}
