package com.E_Commerce.API.Product;

import java.util.List;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductRequest {
    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    @NotBlank(message = "Product description cannot be blank")
    private String productDescription;

    @NotBlank(message = "Product brand cannot be blank")
    private String productBrand;

    @NotNull(message = "Product categories cannot be null")
    private List<String> productCategories;

    @NotNull(message = "Product price cannot be null")
    @Positive(message = "Product price must be greater than zero")
    private Double productPrice;

    @NotNull(message = "Product stock cannot be null")
    @Min(value = 0, message = "Product stock cannot be negative")
    private Integer productStock;

    private String productImage;
}
