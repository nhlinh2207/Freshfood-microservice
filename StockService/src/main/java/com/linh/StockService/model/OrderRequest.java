package com.linh.StockService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderRequest {
    private long productId;
    private long totalAmount;
    private long quantity;
    private PaymentMode paymentMode;
}
