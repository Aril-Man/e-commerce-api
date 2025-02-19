package com.E_Commerce.API.Product;

import java.util.List;

public record ProductResponse(
        String productName,
        String productDescription,
        String productBrand,
        List<String> productCategories,
        Double productPrice,
        Integer productStock,
        String productImage) {

}
