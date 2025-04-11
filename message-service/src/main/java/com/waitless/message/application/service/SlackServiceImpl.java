package com.waitless.message.application.service;

import com.waitless.message.application.dto.SlackSaveDto;
import com.waitless.message.application.mapper.SlackServiceMapper;
import com.waitless.message.domain.repository.SlackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackServiceImpl implements SlackService {

    private final RestTemplate restTemplate;
    private final SlackRepository slackRepository;
    private final SlackServiceMapper slackServiceMapper;

    @Value("${slack.webhook.url}")
    private String webhookUrl;


    @Override
    public void createSlack(String receiverId, Integer mySequence) {
        String fullMessage = String.format("[예약 성공 알림] 예약자: %s 님의 대기 순번은 %d 입니다. %n", receiverId, mySequence);
        Map<String, String> payload = Map.of("text", fullMessage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        try {
            restTemplate.postForEntity(webhookUrl, request, Void.class);

            slackRepository.save(slackServiceMapper.toSlackMessage(new SlackSaveDto(receiverId, fullMessage)));

        } catch (Exception e) {
            log.error("Slack 메시지 전송 실패", e);
        }
    }
}
