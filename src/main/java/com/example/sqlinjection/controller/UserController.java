package com.example.sqlinjection.controller;

import com.example.sqlinjection.model.User;
import com.example.sqlinjection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/find")
    public ResponseEntity<?> findUserByUsername(@RequestParam String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/role")
    public ResponseEntity<List<User>> findUsersByRole(@RequestParam String role) {
        List<User> users = userRepository.findUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }
}
