package com.E_Commerce.API.Cart;

import java.util.List;

public record CartResponse(
                Long id,
                List<CartItemResponse> items,
                Double totalAmount,
                Integer quantity) {

}
