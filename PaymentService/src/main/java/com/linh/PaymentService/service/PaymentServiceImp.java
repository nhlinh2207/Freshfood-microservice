package com.linh.PaymentService.service;

import com.linh.PaymentService.entity.TransactionDetails;
import com.linh.PaymentService.model.PaymentMode;
import com.linh.PaymentService.model.PaymentRequest;
import com.linh.PaymentService.model.PaymentResponse;
import com.linh.PaymentService.repository.TransactionDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class PaymentServiceImp implements PaymentService{

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public long doPayment(PaymentRequest request) {

        log.info("Recording Payment Detail {}", request);

        TransactionDetails tran = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(request.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .build();

        transactionDetailsRepository.saveAndFlush(tran);

        log.info("Transaction complete with Id: {}", tran.getId());

        return tran.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailById(long orderId) {

        log.info("Getting Payment Details for Order : {}", orderId);
        TransactionDetails tran = transactionDetailsRepository.findByOrderId(orderId);

        return PaymentResponse.builder()
                .paymentId(tran.getId())
                .paymentMode(PaymentMode.valueOf(tran.getPaymentMode()))
                .paymentDate(tran.getPaymentDate())
                .orderId(tran.getOrderId())
                .amount(tran.getAmount())
                .status(tran.getPaymentStatus())
                .build();
    }
}
