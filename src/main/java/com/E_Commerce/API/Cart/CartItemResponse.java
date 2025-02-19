package com.E_Commerce.API.Cart;

public record CartItemResponse(
        Long id,
        String name,
        String description,
        String image,
        Double price,
        Integer quantity) {

}
