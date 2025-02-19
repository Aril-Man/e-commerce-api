package com.E_Commerce.API.Cart;

import java.util.List;
import org.springframework.stereotype.Service;

import com.E_Commerce.API.Product.ProductModel;
import com.E_Commerce.API.Product.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
        private final CartRepository cartRepository;
        private final ProductRepository productRepository;

        @Transactional
        public CartResponse addItem(CartItemRequest cartItemRequest) {
                CartModel cart = cartRepository.findById(cartItemRequest.getCartId())
                                .orElseThrow(() -> new RuntimeException("Cart not found"));

                ProductModel productModel = productRepository.findById(cartItemRequest.getProductId())
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                if (cartItemRequest.getQuantity() > productModel.getProductStock()) {
                        throw new RuntimeException("Requested quantity exceeds available stock");
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
                                .orElseThrow(() -> new RuntimeException("Cart not found"));
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
                                .orElseThrow(() -> new RuntimeException("Cart not found"));

                ProductModel productModel = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                CartItem cartItemPresent = cart.getItems().stream()
                                .filter(item -> item.getProduct().getProductNumber().equals(productId))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Item not found"));
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
