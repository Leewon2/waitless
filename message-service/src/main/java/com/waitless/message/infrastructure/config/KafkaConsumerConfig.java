package com.waitless.message.infrastructure.config;

import com.waitless.message.application.event.dto.ReviewRequestEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@EnableKafka
public class KafkaConsumerConfig {
    private <T> ConsumerFactory<String, T> createConsumerFactory(Class<T> targetType) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "message_group");
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConsumerFactory<String, ReviewRequestEvent> reviewRequestConsumerFactory() {
        return createConsumerFactory(ReviewRequestEvent.class);
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReviewRequestEvent> reviewRequestKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ReviewRequestEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reviewRequestConsumerFactory());
        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }


    @Bean
    public DefaultErrorHandler errorHandler() {
        return new DefaultErrorHandler(
                (record, exception) -> log.error("kafka 메시지 처리 실패: {}", record, exception),
                new FixedBackOff(1000L, 2)
        );
    }
}
