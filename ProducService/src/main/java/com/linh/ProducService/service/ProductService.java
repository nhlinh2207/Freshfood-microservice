package com.linh.ProducService.service;

import com.linh.ProducService.model.ProductRequest;
import com.linh.ProducService.model.ProductResponse;

public interface ProductService {

    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long id);

    void reduceQuantity(long id, long quantity);
}
