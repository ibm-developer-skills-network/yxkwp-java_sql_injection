package com.example.sqlinjection.controller;

import com.example.sqlinjection.model.User;
import com.example.sqlinjection.model.Product;
import com.example.sqlinjection.repository.ProductRepository;
import com.example.sqlinjection.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/security-demo")
public class SecurityDemoController {

    @Autowired
    private UserRepositoryImpl userRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/vulnerable/user")
    public ResponseEntity<?> vulnerableUserEndpoint(@RequestParam String username) {
        // This endpoint is vulnerable to SQL injection
        User user = userRepository.findUserByUsername(username);
        
        Map<String, Object> response = new HashMap<>();
        response.put("method", "Vulnerable - String Concatenation");
        response.put("query", "SELECT * FROM users WHERE username = '" + username + "'");
        response.put("result", user);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vulnerable/role")
    public ResponseEntity<?> vulnerableRoleEndpoint(@RequestParam String role) {
        // This endpoint is vulnerable to SQL injection
        List<User> users = userRepository.findUsersByRole(role);
        
        Map<String, Object> response = new HashMap<>();
        response.put("method", "Vulnerable - String Concatenation");
        response.put("query", "SELECT * FROM users WHERE role = '" + role + "'");
        response.put("result", users);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/vulnerable/products")
    public ResponseEntity<?> vulnerableProductEndpoint(
            @RequestParam String category, 
            @RequestParam String price) {
        // This endpoint is vulnerable to SQL injection
        List<Product> products = productRepository.findProductsByCategoryAndPrice(category, price);
        
        Map<String, Object> response = new HashMap<>();
        response.put("method", "Vulnerable - Native Query with String Parameters");
        response.put("query", "SELECT * FROM products WHERE category = '" + category + "' AND price < '" + price + "'");
        response.put("result", products);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/vulnerable/products/search")
    public ResponseEntity<?> vulnerableProductSearchEndpoint(@RequestParam String keyword) {
        // This endpoint is vulnerable to SQL injection
        List<Product> products = productRepository.searchProductsByKeyword(keyword);
        
        Map<String, Object> response = new HashMap<>();
        response.put("method", "Vulnerable - Native Query with String Concatenation");
        response.put("query", "SELECT * FROM products WHERE name LIKE '%" + keyword + "%'");
        response.put("result", products);
        
        return ResponseEntity.ok(response);
    }
}
