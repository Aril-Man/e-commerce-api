package com.E_Commerce.API.Payment;

import java.util.List;

import com.E_Commerce.API.Order.OrderModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderModel> order;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentDate;
    private Double paymentAmount;
}
