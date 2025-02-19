package com.E_Commerce.API.Order;

import java.util.List;

import org.springframework.stereotype.Service;

import com.E_Commerce.API.Cart.CartItem;
import com.E_Commerce.API.Cart.CartModel;
import com.E_Commerce.API.Cart.CartRepository;
import com.E_Commerce.API.Product.ProductModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        CartModel cart = cartRepository.findById(orderRequest.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        List<ProductModel> products = cart.getItems().stream()
                .map(CartItem::getProduct)
                .toList();
        OrderModel order = OrderModel
                .builder()
                .cart(cart)
                .user(cart.getUser())
                .products(products)
                .totalPrice(cart.getTotalPrice())
                .totalItems(cart.getTotalItems())
                .status(Status.ORDERED)
                .build();
        orderRepository.save(order);
        List<OrderProductResponse> orderProducts = products.stream()
                .map(product -> new OrderProductResponse(
                        order.getId(),
                        product.getProductNumber(),
                        product.getProductName(),
                        product.getProductPrice(),
                        cart.getItems().stream()
                                .filter(item -> item.getProduct().getProductNumber().equals(product.getProductNumber()))
                                .findFirst()
                                .map(CartItem::getQuantity)
                                .orElse(0),
                        order.getTotalPrice(),
                        product.getProductImage()))
                .toList();
        return new OrderResponse(
                order.getId(),
                order.getCart().getId(),
                orderProducts,
                order.getUser().getId(),
                order.getTotalPrice(),
                order.getTotalItems(),
                order.getStatus());
    }

    public void deleteOrder(Long orderId) {
        OrderModel order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(Status.CANCELLED);
        orderRepository.save(order);
    }

}
