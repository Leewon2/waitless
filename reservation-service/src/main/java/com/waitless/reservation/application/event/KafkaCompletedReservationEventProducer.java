package com.waitless.reservation.application.event;

import com.waitless.reservation.application.event.dto.CompletedReservationEvent;
import com.waitless.reservation.application.event.dto.ReviewRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaCompletedReservationEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "completed-reservation";

    public void sendCompletedReservation(CompletedReservationEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}
