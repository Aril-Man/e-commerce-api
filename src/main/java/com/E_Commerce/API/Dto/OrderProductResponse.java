package com.E_Commerce.API.Dto;

public record OrderProductResponse(
        Long id,
        Long productId,
        String productName,
        Double price,
        Integer quantity,
        Double totalPrice,
        String imageUrl) {

}
