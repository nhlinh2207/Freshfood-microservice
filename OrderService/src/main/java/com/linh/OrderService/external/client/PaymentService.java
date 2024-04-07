package com.linh.OrderService.external.client;

import com.linh.OrderService.exception.CustomException;
import com.linh.OrderService.external.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker(name="external", fallbackMethod = "fallBack")
@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {

    @PutMapping
    ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

    default void fallBack(Exception e){
        throw new CustomException(
                "Payment Service is not available",
                "UNAVAILABLE",
                500
        );
    }
}
