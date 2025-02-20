package com.E_Commerce.API.Dto;

import lombok.Data;

@Data
public class OrderRequest {
    private Long id;
    private Long cartId;
    private Long userId;
}
