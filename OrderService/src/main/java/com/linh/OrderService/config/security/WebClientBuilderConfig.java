package com.linh.OrderService.config.security;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientBuilderConfig {

    @Bean
    @LoadBalanced // Replace IP , PORT with SERVICE-NAME
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}
