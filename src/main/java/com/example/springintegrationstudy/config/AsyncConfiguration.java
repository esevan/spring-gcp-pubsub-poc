package com.example.springintegrationstudy.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfiguration {
    @Bean
    @Qualifier("publishExecutor")
    public ThreadPoolTaskExecutor publishExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(1);
        threadPoolTaskExecutor.setQueueCapacity(1024);
        threadPoolTaskExecutor.setCorePoolSize(1);

        return threadPoolTaskExecutor;
    }
}
