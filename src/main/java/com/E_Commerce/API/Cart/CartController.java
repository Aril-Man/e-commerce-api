package com.E_Commerce.API.Cart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addItem(@Valid @RequestBody CartItemRequest cartItemRequest) {
        return ResponseEntity.ok(cartService.addItem(cartItemRequest));
    }

    @GetMapping("/get/{cartId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getCart(cartId));
    }

    @DeleteMapping("/remove/{cartId}/{productId}")
    public ResponseEntity<CartResponse> removeItem(@PathVariable Long cartId, @PathVariable Long productId) {
        CartResponse cartResponse = cartService.removeItem(cartId, productId);
        return ResponseEntity.ok(cartResponse);
    }
}
