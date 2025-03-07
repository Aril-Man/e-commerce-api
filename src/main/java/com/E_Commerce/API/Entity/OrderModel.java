package com.E_Commerce.API.Entity;

import java.util.List;

import com.E_Commerce.API.Dto.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CartModel cart;

    @ManyToOne
    private UserModel user;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductModel> products;

    private Double totalPrice;

    private Integer totalItems;

    private Status status;

}
