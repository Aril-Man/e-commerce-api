package com.E_Commerce.API.Repository;

import com.E_Commerce.API.Entity.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentModel, String> {

}
