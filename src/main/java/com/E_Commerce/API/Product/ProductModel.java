package com.E_Commerce.API.Product;

import java.util.List;

import com.E_Commerce.API.Order.OrderModel;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productNumber;
    private String productName;
    private String productDescription;
    private String productBrand;

    @ElementCollection
    private List<String> productCategories;

    private Double productPrice;
    private Integer productStock;
    private String productImage;

    @ManyToMany(mappedBy = "products")
    private List<OrderModel> orders;
}
