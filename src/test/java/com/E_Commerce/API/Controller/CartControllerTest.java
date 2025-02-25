package com.E_Commerce.API.Controller;

import com.E_Commerce.API.Dto.CartItemRequest;
import com.E_Commerce.API.Dto.UserRequestLogin;
import com.E_Commerce.API.Dto.UserResponse;
import com.E_Commerce.API.Repository.CartRepository;
import com.E_Commerce.API.Repository.ProductRepository;
import com.E_Commerce.API.Repository.UserRepository;
import com.E_Commerce.API.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    Faker faker = new Faker();

    @Test
    void createCartSuccess() throws Exception {
        UserResponse userResponse = userService.login(new UserRequestLogin("chu.harvey@hotmail.com", "password"));

        Long cartId = cartRepository.findCartIdByEmail(userResponse.email());
        Long productId = productRepository.findLastId();

        CartItemRequest request = new CartItemRequest();
        request.setCartId(cartId);
        request.setProductId(productId);
        request.setQuantity(Integer.valueOf(faker.number().numberBetween(1, 10)));

        mockMvc.perform(
                post("/api/v1/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer " + userResponse.token())
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void createCartUnauthorized() throws Exception {
        Long cartId = cartRepository.findCartIdByEmail("chu.harvey@hotmail.com");
        Long productId = productRepository.findLastId();

        CartItemRequest request = new CartItemRequest();
        request.setCartId(cartId);
        request.setProductId(productId);
        request.setQuantity(Integer.valueOf(faker.number().numberBetween(1, 10)));

        mockMvc.perform(
                post("/api/v1/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getCartSuccess() throws Exception {
        UserResponse userResponse = userService.login(new UserRequestLogin("chu.harvey@hotmail.com", "password"));
        Long cartId = cartRepository.findCartIdByEmail(userResponse.email());

        mockMvc.perform(
                get("/api/v1/cart/get/" + cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userResponse.token())
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void getCartUnauthorized() throws Exception {
        Long cartId = cartRepository.findCartIdByEmail("chu.harvey@hotmail.com");

        mockMvc.perform(
                get("/api/v1/cart/get/" + cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }
}