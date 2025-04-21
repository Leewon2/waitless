package com.waitless.message.application.event;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.waitless.common.exception.BusinessException;
import com.waitless.message.application.event.dto.CompletedReservationEvent;
import com.waitless.message.application.event.dto.ReviewRequestEvent;
import com.waitless.message.application.service.SlackService;
import com.waitless.message.exception.MessageErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSlackEventConsumer {

    private final SlackService slackService;

    @KafkaListener(topics = "review-request", groupId = "message-group")
    public void reviewRequest(String messageJson) {
        try{
            ReviewRequestEvent event = new ObjectMapper().readValue(messageJson, ReviewRequestEvent.class);
            slackService.createReviewRequestSlack(event.slackId(), event.message());
        }catch (Exception e){
            throw BusinessException.from(MessageErrorCode.REVIEW_REQUEST_KAFKA_ERROR);
        }
    }

    @KafkaListener(topics = "completed-reservation", groupId = "message-group")
    public void completedReservation(String messageJson) {
        try{
            CompletedReservationEvent event = new ObjectMapper().readValue(messageJson, CompletedReservationEvent.class);
            slackService.createSlack(event.slackId(), event.sequence());
        }catch (Exception e){
            throw BusinessException.from(MessageErrorCode.REVIEW_REQUEST_KAFKA_ERROR);
        }
    }
}
