package com.example.recruitmenttaskv01.WebClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient githubWebClient(WebClient.Builder webClientBuilder, @Value("${github_base_url}") String githubBaseUrl) {
        return webClientBuilder.baseUrl(githubBaseUrl).build();
    }
}
