package com.E_Commerce.API.Service;

import java.util.List;

import com.E_Commerce.API.Dto.OrderProductResponse;
import com.E_Commerce.API.Dto.OrderRequest;
import com.E_Commerce.API.Dto.OrderResponse;
import com.E_Commerce.API.Dto.Status;
import com.E_Commerce.API.Entity.OrderModel;
import com.E_Commerce.API.Repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.E_Commerce.API.Entity.CartItem;
import com.E_Commerce.API.Entity.CartModel;
import com.E_Commerce.API.Repository.CartRepository;
import com.E_Commerce.API.Entity.ProductModel;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        CartModel cart = cartRepository.findById(orderRequest.getCartId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        order.setStatus(Status.CANCELLED);
        orderRepository.save(order);
    }

}
