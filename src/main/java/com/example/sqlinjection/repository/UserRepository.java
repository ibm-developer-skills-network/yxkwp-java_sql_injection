package com.example.sqlinjection.repository;

import com.example.sqlinjection.model.User;
import java.util.List;

public interface UserRepository {
    
    User findUserByUsername(String username);
    
    List<User> findUsersByRole(String role);
    
    User saveUser(User user);
}
