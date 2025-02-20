package com.E_Commerce.API.Repository;

import com.E_Commerce.API.Entity.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {

}
