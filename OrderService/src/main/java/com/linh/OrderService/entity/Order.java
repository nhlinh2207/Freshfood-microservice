package com.linh.OrderService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "ORDER_DETAILS")
public class Order {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private long id;

     @Column(name = "PRODUCT_ID")
     private long productId;

     @Column(name = "QUANTITY")
     private long quantity;

     @Column(name = "ORDER_DATE")
     private Instant orderDate;

     @Column(name = "PRODUCT_STATUS")
     private String orderStatus;

     @Column(name = "TOTAL_AMOUNT")
     private long amount;


}
