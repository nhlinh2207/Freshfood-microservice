package com.linh.PaymentService.controller;

import com.linh.PaymentService.model.PaymentRequest;
import com.linh.PaymentService.model.PaymentResponse;
import com.linh.PaymentService.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/payment")
@Slf4j
public class PaymentController {

      @Autowired
      private PaymentService paymentService;

      @PutMapping
      public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest){
          return new ResponseEntity<>(
                  paymentService.doPayment(paymentRequest),
                  HttpStatus.OK
          );
      }

      @GetMapping("/order/{orderId}")
      public ResponseEntity<PaymentResponse> getPaymentDetailById(@PathVariable long orderId){
          return new ResponseEntity<>(
                  paymentService.getPaymentDetailById(orderId),
                  HttpStatus.OK
          );
      }
}
