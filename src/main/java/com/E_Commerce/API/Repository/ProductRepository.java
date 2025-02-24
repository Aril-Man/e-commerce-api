package com.E_Commerce.API.Repository;

import com.E_Commerce.API.Entity.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    @Query(value = "SELECT product_number FROM product_model ORDER BY product_number DESC LIMIT 1", nativeQuery = true)
    Long findLastId();
}
