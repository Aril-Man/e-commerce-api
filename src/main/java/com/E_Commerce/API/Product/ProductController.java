package com.E_Commerce.API.Product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(
            @Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(
                productService.addProduct(productRequest));
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @Valid @RequestBody ProductRequest productRequest,
            @PathVariable Long productId) {
        return ResponseEntity.ok(
                productService.updateProduct(productRequest, productId));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable Long productId) {
        return ResponseEntity.ok(
                productService.getProduct(productId));
    }
}
