package com.linh.PaymentService.service;

import com.linh.PaymentService.model.PaymentRequest;
import com.linh.PaymentService.model.PaymentResponse;

public interface PaymentService {

    long doPayment(PaymentRequest request);

    PaymentResponse getPaymentDetailById(long paymentId);

}
