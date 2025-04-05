package com.example.sqlinjection.controller;

import com.example.sqlinjection.model.Product;
import com.example.sqlinjection.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductsByCategory(
            @RequestParam String category, 
            @RequestParam String price) {
        // This endpoint is vulnerable to SQL injection through the parameters
        List<Product> products = productRepository.findProductsByCategoryAndPrice(category, price);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/keyword")
    public ResponseEntity<List<Product>> searchProductsByKeyword(@RequestParam String keyword) {
        // This endpoint is also vulnerable to SQL injection
        List<Product> products = productRepository.searchProductsByKeyword(keyword);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }
}
