package com.waitless.message.application.service;

import com.waitless.message.application.dto.SlackDeleteResponseDto;
import com.waitless.message.application.dto.SlackSaveDto;
import com.waitless.message.application.mapper.SlackServiceMapper;
import com.waitless.message.domain.entity.FailedSlackMessage;
import com.waitless.message.domain.entity.SlackMessage;
import com.waitless.message.domain.repository.SlackFailRepository;
import com.waitless.message.domain.repository.SlackRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackServiceImpl implements SlackService {

    private final RestTemplate restTemplate;
    private final SlackRepository slackRepository;
    private final SlackServiceMapper slackServiceMapper;
    private final SlackSelect slackSelect;
    private final SlackFailRepository slackFailRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final Executor executor = Executors.newFixedThreadPool(10);

    @Override
    @Retry(name = "slackRetry", fallbackMethod = "fallbackSendSlack")
    @RateLimiter(name = "slackRateLimiter", fallbackMethod = "fallbackSendSlack")
    @CircuitBreaker(name = "slackCircuitBreaker", fallbackMethod = "fallbackSendSlack")
    @Bulkhead(name = "slackBulkhead", fallbackMethod = "fallbackSendSlack")
    @TimeLimiter(name = "slackTimeLimiter", fallbackMethod = "fallbackSendSlack")
    public CompletableFuture<SlackMessage> createSlack(String receiverId, Integer mySequence) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String fullMessage = makeMessage(receiverId, mySequence);
                Map<String, String> payload = Map.of("text", fullMessage);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

                String webhookUrl = slackSelect.getWebhookUrl();
                ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, request, String.class);

                return slackRepository.save(slackServiceMapper.toSlackMessage(new SlackSaveDto(receiverId, fullMessage)));
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }, executor);
    }

    private CompletableFuture<SlackMessage> fallbackSendSlack(String receiverId, Integer mySequence, Throwable t) {
        String fullMessage = makeMessage(receiverId, mySequence);
        if (checkDuplicate(receiverId + ":" + mySequence)) {
            return CompletableFuture.completedFuture(null);
        }
        slackFailRepository.save(new FailedSlackMessage(receiverId, fullMessage, mySequence, 0, false));
        return CompletableFuture.completedFuture(null);
    }

    @Transactional
    @Override
    public SlackDeleteResponseDto deleteMessage(UUID id) {
        SlackMessage slackMessage = slackRepository.findById(id).orElseThrow(() -> new NullPointerException("슬랙 메세지 없음"));
        slackMessage.delete();
        return new SlackDeleteResponseDto(slackMessage.getId(), slackMessage.getReceiverId(), slackMessage.getMessage(), slackMessage.isDeleted());
    }

    private String makeMessage(String receiverId, Integer mySequence) {
        return String.format("[예약 성공 알림] 예약자: %s 님의 대기 순번은 %d 입니다.%n", receiverId, mySequence);
    }

    public boolean checkDuplicate(String messageKey) {
        boolean success = stringRedisTemplate.opsForValue().setIfAbsent(messageKey, "value", Duration.ofDays(1));
        return !success;
    }
}
