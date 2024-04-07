package com.linh.OrderService.repository;

import com.linh.OrderService.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
