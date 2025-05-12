package com.waitless.message.application.service;

import com.waitless.message.domain.entity.FailedSlackMessage;
import com.waitless.message.domain.entity.SlackMessage;
import com.waitless.message.domain.repository.SlackFailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackFailService {
    private final SlackFailRepository slackFailRepository;
    private final SlackService slackService;

    @Scheduled(fixedDelay = 3000)
    public void retryFailedMessages() {
        List<FailedSlackMessage> failedList = slackFailRepository.findAllByRetryCompletedFalseOrderByCreatedAtAsc();

        for (FailedSlackMessage failedMessage : failedList) {
            try {
                SlackMessage result =slackService.createSlack(failedMessage.getReceiverId(), failedMessage.getMySequence()).get();
                if (result != null) {
                    failedMessage.success();
                } else {
                    failedMessage.increaseRetryCount();
                }
                Thread.sleep(350);
            } catch (Exception e) {
                failedMessage.increaseRetryCount();
            }
        }

        slackFailRepository.saveAll(failedList);
    }
}
