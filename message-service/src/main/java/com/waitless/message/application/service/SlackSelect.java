package com.waitless.message.application.service;

import com.waitless.message.infrastructure.config.SlackWebhookProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class SlackSelect {
    private final SlackWebhookProperties slackWebhookProperties;
    private final AtomicInteger count = new AtomicInteger(0);

    public SlackSelect(SlackWebhookProperties slackWebhookProperties) {
        this.slackWebhookProperties = slackWebhookProperties;
    }

    public String getWebhookUrl() {
        var urls = slackWebhookProperties.getUrls();
        int idx = count.getAndUpdate(i -> (i + 1) % urls.size());
        return urls.get(idx);
    }
}
