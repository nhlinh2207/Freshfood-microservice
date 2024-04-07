package com.linh.ProducService.repository;

import com.linh.ProducService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
