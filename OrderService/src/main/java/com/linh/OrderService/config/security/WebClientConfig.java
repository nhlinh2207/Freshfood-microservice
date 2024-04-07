package com.linh.OrderService.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Bean
    public WebClient webClient(){
        return webClientBuilder
                .filter(new ServletBearerExchangeFilterFunction())
                .build();
    }

}
