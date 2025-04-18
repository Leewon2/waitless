package com.waitless.reservation.application.event;

import com.waitless.reservation.application.event.dto.ReviewRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSlackEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "review-request";

    public void sendReviewRequest(ReviewRequestEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}
