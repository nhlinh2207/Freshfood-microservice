package com.linh.StockService;

import com.linh.StockService.config.KafkaErrorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.CommonErrorHandler;

@SpringBootApplication
public class StockServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockServiceApplication.class, args);
	}

	@Bean
	CommonErrorHandler commonErrorHandler() {
        return new KafkaErrorHandler();
    }
}
