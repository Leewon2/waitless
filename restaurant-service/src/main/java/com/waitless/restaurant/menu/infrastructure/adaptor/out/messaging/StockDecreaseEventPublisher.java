package com.waitless.restaurant.menu.infrastructure.adaptor.out.messaging;

import com.waitless.common.event.StockDecreasedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockDecreaseEventPublisher {

    private final KafkaTemplate<String, StockDecreasedEvent> kafkaTemplate;

    private static final String TOPIC_STOCK_DECREASE_FAILED = "restaurant-stock-decrease-failed-events";

    public void publishStockDecreaseFailed(StockDecreasedEvent event) {

        kafkaTemplate.send(TOPIC_STOCK_DECREASE_FAILED,"restaurant-stock-decrease-failed", event);
        log.info("[kafka] StockDecreaseIssuedFailedEvent 발행 완료: {}", event);
    }
}
