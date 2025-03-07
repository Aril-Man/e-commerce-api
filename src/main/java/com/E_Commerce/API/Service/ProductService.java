package com.E_Commerce.API.Service;

import com.E_Commerce.API.Dto.ProductRequest;
import com.E_Commerce.API.Dto.ProductResponse;
import com.E_Commerce.API.Entity.ProductModel;
import com.E_Commerce.API.Repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse addProduct(ProductRequest productRequest) {
        ProductModel product = ProductModel
                .builder()
                .productName(productRequest.getProductName())
                .productDescription(productRequest.getProductDescription())
                .productBrand(productRequest.getProductBrand())
                .productCategories(productRequest.getProductCategories())
                .productPrice(productRequest.getProductPrice())
                .productStock(productRequest.getProductStock())
                .productImage(productRequest.getProductImage())
                .build();
        productRepository.save(product);
        return new ProductResponse(product.getProductName(),
                product.getProductDescription(),
                product.getProductBrand(),
                product.getProductCategories(),
                product.getProductPrice(),
                product.getProductStock(),
                product.getProductImage());
    }

    public void deleteProduct(Long productId) {
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        productRepository.delete(product);
    }

    public ProductResponse getProduct(Long productId) {
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return new ProductResponse(product.getProductName(),
                product.getProductDescription(),
                product.getProductBrand(),
                product.getProductCategories(),
                product.getProductPrice(),
                product.getProductStock(),
                product.getProductImage());
    }

    public ProductResponse updateProduct(ProductRequest productRequest, Long productId) {
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        if (productRequest.getProductName() != null) {
            product.setProductName(productRequest.getProductName());
        }

        if (productRequest.getProductDescription() != null) {
            product.setProductDescription(productRequest.getProductDescription());
        }

        if (productRequest.getProductBrand() != null) {
            product.setProductBrand(productRequest.getProductBrand());
        }

        if (productRequest.getProductCategories() != null) {
            product.setProductCategories(productRequest.getProductCategories());
        }

        if (productRequest.getProductPrice() != null) {
            product.setProductPrice(productRequest.getProductPrice());
        }

        if (productRequest.getProductStock() != null) {
            product.setProductStock(productRequest.getProductStock());
        }

        if (productRequest.getProductImage() != null) {
            product.setProductImage(productRequest.getProductImage());
        }

        productRepository.save(product);

        return new ProductResponse(product.getProductName(),
                product.getProductDescription(),
                product.getProductBrand(),
                product.getProductCategories(),
                product.getProductPrice(),
                product.getProductStock(),
                product.getProductImage());
    }

}
