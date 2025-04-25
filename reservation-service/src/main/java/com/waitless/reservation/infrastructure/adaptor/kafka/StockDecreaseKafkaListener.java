package com.waitless.reservation.infrastructure.adaptor.kafka;

import com.waitless.common.event.StockDecreasedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StockDecreaseKafkaListener {
    private static final String TOPIC_NAME = "restaurant-stock-decrease";
    private static final String GROUP_ID = "restaurant-stock-consumer";

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    public void consume(StockDecreasedEvent event) {

    }
}
