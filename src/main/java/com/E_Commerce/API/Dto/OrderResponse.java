package com.E_Commerce.API.Dto;

import java.util.List;

public record OrderResponse(
        Long id,
        Long cartId,
        List<OrderProductResponse> products,
        Long userId,
        Double totalPrice,
        Integer totalItems,
        Status status) {

}
