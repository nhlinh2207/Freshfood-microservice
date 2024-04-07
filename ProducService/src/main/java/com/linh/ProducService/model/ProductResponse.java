package com.linh.ProducService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private long productId;
    private String productName;
    private long price;
    private long quantity;
}
