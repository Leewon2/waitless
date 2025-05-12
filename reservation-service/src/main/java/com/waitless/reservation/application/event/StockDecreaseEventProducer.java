package com.waitless.reservation.application.event;

import com.waitless.common.event.StockDecreasedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockDecreaseEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "restaurant-stock-decrease";

    public void publish(StockDecreasedEvent event) {
        kafkaTemplate.send(TOPIC,"stock-key",  event);
    }
}
