package com.E_Commerce.API.Repository;

import com.E_Commerce.API.Entity.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<CartModel, Long> {

    @Query(value = "SELECT cart_model.id FROM cart_model " +
            "LEFT JOIN user_model ON cart_model.user_id = user_model.id " +
            "WHERE user_model.email = :email", nativeQuery = true)
    Long findCartIdByEmail(@Param("email") String email);
}
