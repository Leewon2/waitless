package com.waitless.reservation.application.event;

import com.waitless.common.dto.ReservationCancelEvent;
import com.waitless.common.event.VisitRequestMessageEvent;
import com.waitless.reservation.application.event.dto.ReviewRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSlackEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String REVIEW_TOPIC = "review-request";
    private static final String CANCEL_TOPIC = "cancel-request";
    private static final String VISIT_TOPIC = "visit-request";


    public void sendReviewRequest(ReviewRequestEvent event) {
        kafkaTemplate.send(REVIEW_TOPIC, event);
    }

    public void sendCancelRequest(ReservationCancelEvent event) {
        kafkaTemplate.send(CANCEL_TOPIC, event);
    }

    public void sendVisitRequest(VisitRequestMessageEvent event) {
        kafkaTemplate.send(VISIT_TOPIC, event);
    }
}
