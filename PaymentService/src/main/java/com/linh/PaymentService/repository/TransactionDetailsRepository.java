package com.linh.PaymentService.repository;

import com.linh.PaymentService.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {

    TransactionDetails findByOrderId(long orderId);
}
