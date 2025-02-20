package com.E_Commerce.API.Dto;

public record CartItemResponse(
        Long id,
        String name,
        String description,
        String image,
        Double price,
        Integer quantity) {

}
