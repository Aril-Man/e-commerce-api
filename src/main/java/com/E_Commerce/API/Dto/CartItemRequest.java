package com.E_Commerce.API.Dto;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long cartId;
    private Long productId;
    private Integer quantity;
}
