package com.linh.OrderService.external.request;

import com.linh.OrderService.model.PaymentMode;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentRequest {

    private long orderId;
    private long amount;
    private String referenceNumber;
    private PaymentMode paymentMode;
}
