package com.E_Commerce.API.Repository;

import com.E_Commerce.API.Entity.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

}
