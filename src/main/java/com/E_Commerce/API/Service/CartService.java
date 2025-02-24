package com.E_Commerce.API.Service;

import java.util.List;

import com.E_Commerce.API.Dto.CartItemRequest;
import com.E_Commerce.API.Dto.CartItemResponse;
import com.E_Commerce.API.Dto.CartResponse;
import com.E_Commerce.API.Entity.CartItem;
import com.E_Commerce.API.Entity.CartModel;
import com.E_Commerce.API.Repository.CartRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.E_Commerce.API.Entity.ProductModel;
import com.E_Commerce.API.Repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CartService {
        private final CartRepository cartRepository;
        private final ProductRepository productRepository;

        @Transactional
        public CartResponse addItem(CartItemRequest cartItemRequest) {
                CartModel cart = cartRepository.findById(cartItemRequest.getCartId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

                ProductModel productModel = productRepository.findById(cartItemRequest.getProductId())
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

                if (cartItemRequest.getQuantity() > productModel.getProductStock()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requested quantity exceeds available stock");
                }
                CartItem existingCartItem = cart.getItems().stream()
                                .filter(cartItem -> cartItem.getProduct().getProductNumber()
                                                .equals(productModel.getProductNumber()))
                                .findFirst()
                                .orElse(null);
                if (existingCartItem != null) {
                        existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
                } else {
                        CartItem newItem = CartItem
                                        .builder()
                                        .cart(cart)
                                        .product(productModel)
                                        .quantity(cartItemRequest.getQuantity())
                                        .build();
                        cart.getItems().add(newItem);
                        newItem.setCart(cart);
                }

                List<CartItemResponse> items = cart.getItems().stream()
                                .map(cartItem -> new CartItemResponse(
                                                cartItem.getProduct().getProductNumber(),
                                                cartItem.getProduct().getProductName(),
                                                cartItem.getProduct().getProductDescription(),
                                                cartItem.getProduct().getProductImage(),
                                                cartItem.getProduct().getProductPrice(),
                                                cartItem.getQuantity()))
                                .toList();

                productModel.setProductStock(productModel.getProductStock() - cartItemRequest.getQuantity());
                productRepository.save(productModel);

                cart.setTotalItems(cart.getTotalItems() + cartItemRequest.getQuantity());
                cart.setTotalPrice(
                                cart.getTotalPrice() + productModel.getProductPrice() * cartItemRequest.getQuantity());
                cartRepository.save(cart);
                return new CartResponse(cart.getId(),
                                items,
                                cart.getTotalPrice(),
                                cart.getTotalItems());
        }

        public CartResponse getCart(Long cartId) {
                CartModel cart = cartRepository.findById(cartId)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
                List<CartItemResponse> items = cart.getItems().stream().map(
                                cartItem -> new CartItemResponse(
                                                cartItem.getProduct().getProductNumber(),
                                                cartItem.getProduct().getProductName(),
                                                cartItem.getProduct().getProductDescription(),
                                                cartItem.getProduct().getProductImage(),
                                                cartItem.getProduct().getProductPrice(),
                                                cartItem.getQuantity()))
                                .toList();
                return new CartResponse(cart.getId(),
                                items,
                                cart.getTotalPrice(),
                                cart.getTotalItems());
        }

        @Transactional
        public CartResponse removeItem(Long cartId, Long productId) {
                CartModel cart = cartRepository.findById(cartId)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

                ProductModel productModel = productRepository.findById(productId)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

                CartItem cartItemPresent = cart.getItems().stream()
                                .filter(item -> item.getProduct().getProductNumber().equals(productId))
                                .findFirst()
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
                cartItemPresent.setQuantity(cartItemPresent.getQuantity() - 1);
                productModel.setProductStock(productModel.getProductStock() + 1);
                if (cartItemPresent.getQuantity() == 0) {
                        cart.getItems().remove(cartItemPresent);
                        cartRepository.save(cart);
                }
                cart.setTotalItems(cart.getItems().stream().mapToInt(CartItem::getQuantity).sum());
                cart.setTotalPrice(cart.getItems().stream()
                                .mapToDouble(item -> item.getProduct().getProductPrice() * item.getQuantity()).sum());

                productRepository.save(productModel);
                cartRepository.save(cart);
                List<CartItemResponse> items = cart.getItems().stream().map(
                                cartItem -> new CartItemResponse(
                                                cartItem.getProduct().getProductNumber(),
                                                cartItem.getProduct().getProductName(),
                                                cartItem.getProduct().getProductDescription(),
                                                cartItem.getProduct().getProductImage(),
                                                cartItem.getProduct().getProductPrice(),
                                                cartItem.getQuantity()))
                                .toList();
                return new CartResponse(cart.getId(),
                                items,
                                cart.getTotalPrice(),
                                cart.getTotalItems());
        }

}
