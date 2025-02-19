package com.E_Commerce.API.Order;

import lombok.Data;

@Data
public class OrderRequest {
    private Long id;
    private Long cartId;
    private Long userId;
}
