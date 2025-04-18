package com.waitless.reservation.infrastructure.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "messageExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4); // 기본 쓰레드 수
        executor.setMaxPoolSize(10); // 최대 쓰레드 수
        executor.setQueueCapacity(100); // 큐 수용량
        executor.setThreadNamePrefix("messageExecutor-");
        executor.initialize();
        return executor;
    }
}