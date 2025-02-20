package com.E_Commerce.API.Repository;

import com.E_Commerce.API.Entity.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartModel, Long> {

}
