package com.linh.ProducService.controller;

import com.linh.ProducService.model.ProductRequest;
import com.linh.ProducService.model.ProductResponse;
import com.linh.ProducService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
         return ResponseEntity.ok(productService.addProduct(productRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable long id, @RequestParam long quantity) {
        productService.reduceQuantity(id, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/test_security")
    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    public String testSecurity() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return "Scopes: " + authentication.getAuthorities();
    }
}
