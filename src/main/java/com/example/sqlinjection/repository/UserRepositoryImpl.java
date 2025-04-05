package com.example.sqlinjection.repository;

import com.example.sqlinjection.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public User findUserByUsername(String username) {
        // VULNERABLE CODE: Direct string concatenation
        String sql = "SELECT * FROM users WHERE username = '" + username + "'";
        
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
        
        return users.isEmpty() ? null : users.get(0);
    }
    
    @Override
    public List<User> findUsersByRole(String role) {
        // VULNERABLE CODE: Another example of string concatenation
        String sql = "SELECT * FROM users WHERE role = '" + role + "'";
        
        return jdbcTemplate.query(sql, new UserRowMapper());
    }
    
    @Override
    public User saveUser(User user) {
        // This is also vulnerable as it directly uses user input in SQL
        String sql = "INSERT INTO users (username, password, email, role) VALUES ('" 
                    + user.getUsername() + "', '" 
                    + user.getPassword() + "', '" 
                    + user.getEmail() + "', '" 
                    + user.getRole() + "')";
        
        jdbcTemplate.update(sql);
        
        // Return the saved user (in a real app, we'd get the generated ID)
        return user;
    }
    
    // Row mapper for User entities
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            return user;
        }
    }
}
