package com.waitless.reservation.infrastructure.adaptor.kafka;

import com.waitless.common.event.StockDecreasedEvent;
import com.waitless.reservation.application.service.command.ReservationCommandService;
import com.waitless.reservation.application.service.redis.RedisStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StockDecreaseKafkaListener {
    private final ReservationCommandService reservationCommandService;
    private static final String TOPIC_NAME = "restaurant-stock-decrease-failed-events";
    private static final String GROUP_ID = "restaurant-stock-fail-consumer";

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    public void consume(StockDecreasedEvent event) {
        reservationCommandService.cancelReservation(event.getReservationId());
    }
}
