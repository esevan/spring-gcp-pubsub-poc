package com.example.springintegrationstudy.config;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.TopicName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class PublisherConfiguration {

    @Value("${spring.cloud.gcp.pubsub.topics.table-event}")
    private String tableEventTopic;

    @Value("${spring.cloud.gcp.project-id}")
    private String project;

    @Bean
    @Qualifier("tablePublisher")
    public Publisher tablePublisher() {
        try {
            return Publisher.newBuilder(TopicName.of(project, tableEventTopic))
                    .setEnableMessageOrdering(true)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("table publisher initialization error");
        }
    }
}
