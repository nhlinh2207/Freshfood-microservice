package com.linh.OrderService.model;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderResponse {

    private long orderId;
    private Instant orderDate;
    private String orderStatus;
    private long amount;
    private ProductDetails productDetails;
    private PaymentDetails paymentDetails;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductDetails{
          private String productName;
          private long productId;
          private long quantity;
          private long price;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PaymentDetails{
        private long paymentId;
        private PaymentMode paymentMode;
        private String paymentStatus;
        private Instant paymentDate;
    }
}
