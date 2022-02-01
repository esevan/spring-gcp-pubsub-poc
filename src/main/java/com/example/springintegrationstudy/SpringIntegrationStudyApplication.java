package com.example.springintegrationstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class SpringIntegrationStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationStudyApplication.class, args);
    }

}
