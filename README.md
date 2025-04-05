# SQL Injection Vulnerability Demo

This Spring Boot application demonstrates common SQL injection vulnerabilities and how they can be detected. The project uses JDK 21 and Maven.

## Overview

This application contains deliberately vulnerable code to demonstrate SQL injection attacks. It includes:

1. Direct string concatenation in JDBC queries
2. Unsafe parameter usage in native JPA queries
3. Endpoints that expose these vulnerabilities

## Vulnerabilities Demonstrated

### 1. JDBC String Concatenation

In `UserRepositoryImpl.java`:
```java
// VULNERABLE CODE: Direct string concatenation
String sql = "SELECT * FROM users WHERE username = '" + username + "'";
```

This allows attacks like: `' OR '1'='1`

### 2. Unsafe Native Queries in JPA

In `ProductRepository.java`:
```java
// VULNERABLE CODE: Unsafe parameter inclusion in native query
@Query(value = "SELECT * FROM products WHERE category = :category AND price < :price", nativeQuery = true)
List<Product> findProductsByCategoryAndPrice(@Param("category") String category, @Param("price") String price);
```

This allows attacks through the price parameter like: `999.99' OR '1'='1`

## How to Test

1. Start the application
2. Use the H2 console at http://localhost:8080/h2-console to view the database
3. Try SQL injection attacks on the following endpoints:
   - GET `/api/users/find?username=admin' OR '1'='1`
   - GET `/api/products/search?category=Electronics&price=100' OR '1'='1`

## Security Best Practices

To fix these vulnerabilities:
1. Use prepared statements with parameterized queries
2. Use JPA's built-in query methods or JPQL with parameters
3. Validate and sanitize all user inputs
4. Implement proper error handling to avoid exposing database details