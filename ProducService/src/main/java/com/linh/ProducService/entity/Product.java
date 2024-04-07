package com.linh.ProducService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "QUANTITY")
    private long quantity;

    @Column(name = "PRICE")
    private long price;
}
