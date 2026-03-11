package com.nana.torneos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient usersWebClient() {
        return WebClient.builder()
                .baseUrl("http://users-service:8081/api/v1/users")
                .build();
    }
}