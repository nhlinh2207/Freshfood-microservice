package com.linh.ProducService.service;

import com.linh.ProducService.entity.Product;
import com.linh.ProducService.exception.ProductServiceCustomException;
import com.linh.ProducService.model.ProductRequest;
import com.linh.ProducService.model.ProductResponse;
import com.linh.ProducService.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {

        log.info("Add product....");

        Product product = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();

        productRepository.saveAndFlush(product);

        log.info("Product created....");

        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long id) {
        log.info("AGet product by id....");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductServiceCustomException("Can not find Product by id: "+id, "PRODUCT_NOT_FOUND"));

        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(product, response);
        return response;
    }

    @Override
    public void reduceQuantity(long id, long quantity) {
        log.info("Reduce product quantity....");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductServiceCustomException("Can not find Product by id: "+id, "PRODUCT_NOT_FOUND"));

        if (product.getQuantity() < quantity){
            throw new ProductServiceCustomException("Product doesn't have enough quantity", "NOT_INSUFFICIENT_QUANTITY");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.saveAndFlush(product);

        log.info("Reduce quantity successfully....");
    }
}
