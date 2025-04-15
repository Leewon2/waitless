package com.waitless.message.infrastructure.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@ConfigurationProperties(prefix = "slack.webhook")
public class SlackWebhookProperties {

    private final List<String> urls;

    @ConstructorBinding
    public SlackWebhookProperties(List<String> urls) {
        this.urls = urls;
    }

}
