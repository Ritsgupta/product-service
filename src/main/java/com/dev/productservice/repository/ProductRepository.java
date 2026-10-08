package com.dev.productservice.repository;

import com.dev.productservice.entity.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Derived Query Method
    Optional<Product> findBySku(String sku);

    // Custom JPQL Query
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findProductsInPriceRange(@Param("minPrice") BigDecimal minPrice,
                                           @Param("maxPrice") BigDecimal maxPrice);

    // Native SQL Query (Vendor-specific execution)
    @Query(value = "SELECT * FROM products WHERE stock_quantity < :threshold", nativeQuery = true)
    List<Product> findLowStockProductsNative(@Param("threshold") int threshold);
}