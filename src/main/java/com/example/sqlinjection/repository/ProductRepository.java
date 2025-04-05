package com.example.sqlinjection.repository;

import com.example.sqlinjection.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // VULNERABLE CODE: Unsafe parameter inclusion in native query
    @Query(value = "SELECT * FROM products WHERE category = :category AND price < :price", nativeQuery = true)
    List<Product> findProductsByCategoryAndPrice(@Param("category") String category, @Param("price") String price);
    
    // Another vulnerable query using string concatenation in the repository implementation
    @Query(value = "SELECT * FROM products WHERE name LIKE '%' || :keyword || '%'", nativeQuery = true)
    List<Product> searchProductsByKeyword(@Param("keyword") String keyword);
}
