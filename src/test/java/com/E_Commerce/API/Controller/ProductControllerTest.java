package com.E_Commerce.API.Controller;

import com.E_Commerce.API.Dto.*;
import com.E_Commerce.API.Entity.UserModel;
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

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    Faker faker = new Faker();

    @Test
    void createProductSuccess() throws Exception {

        UserResponse userResponse = userService.login(new UserRequestLogin("jolynn.klocko@yahoo.com", "password"));

        System.out.println(userResponse);

        ProductRequest request = new ProductRequest();
        request.setProductName(faker.commerce().productName());
        request.setProductPrice(Double.valueOf(faker.commerce().price()));
        request.setProductCategories(Collections.singletonList(faker.commerce().department()));
        request.setProductBrand(faker.commerce().department());
        request.setProductDescription(faker.commerce().color());
        request.setProductImage(faker.internet().url());
        request.setProductStock(Integer.valueOf(faker.number().numberBetween(1, 100)));

        mockMvc.perform(
                post("/api/v1/product/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer " + userResponse.token())
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void createProductUnauthorized() throws Exception {

        ProductRequest request = new ProductRequest();
        request.setProductName(faker.commerce().productName());
        request.setProductPrice(Double.valueOf(faker.commerce().price()));
        request.setProductCategories(Collections.singletonList(faker.commerce().department()));
        request.setProductBrand(faker.commerce().department());
        request.setProductDescription(faker.commerce().color());
        request.setProductImage(faker.internet().url());
        request.setProductStock(Integer.valueOf(faker.number().numberBetween(1, 100)));

        mockMvc.perform(
                post("/api/v1/product/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    void updateProductSuccess() throws Exception {

        UserResponse userResponse = userService.login(new UserRequestLogin("jolynn.klocko@yahoo.com", "password"));

        ProductRequest request = new ProductRequest();
        request.setProductName(faker.commerce().productName());
        request.setProductPrice(Double.valueOf(faker.commerce().price()));
        request.setProductCategories(Collections.singletonList(faker.commerce().department()));
        request.setProductBrand(faker.commerce().department());
        request.setProductDescription(faker.commerce().color());
        request.setProductImage(faker.internet().url());
        request.setProductStock(Integer.valueOf(faker.number().numberBetween(1, 100)));

        mockMvc.perform(
                put("/api/v1/product/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer " + userResponse.token())
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void updateProductUnauthorized() throws Exception {

        ProductRequest request = new ProductRequest();
        request.setProductName(faker.commerce().productName());
        request.setProductPrice(Double.valueOf(faker.commerce().price()));
        request.setProductCategories(Collections.singletonList(faker.commerce().department()));
        request.setProductBrand(faker.commerce().department());
        request.setProductDescription(faker.commerce().color());
        request.setProductImage(faker.internet().url());
        request.setProductStock(Integer.valueOf(faker.number().numberBetween(1, 100)));

        mockMvc.perform(
                put("/api/v1/product/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getProductSuccess() throws Exception {

        UserResponse userResponse = userService.login(new UserRequestLogin("jolynn.klocko@yahoo.com", "password"));

        mockMvc.perform(
                get("/api/v1/product/get/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userResponse.token())
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(
                        result -> {
                            ProductResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ProductResponse.class);
                            assertNotNull(response);
                        }
                );
    }
}