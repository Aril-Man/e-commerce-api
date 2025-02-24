package com.E_Commerce.API.Controller;

import com.E_Commerce.API.Dto.UserRequestDto;
import com.E_Commerce.API.Dto.UserRequestLogin;
import com.E_Commerce.API.Dto.UserResponse;
import com.E_Commerce.API.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    Faker faker = new Faker();

    @Test
    void registerFail() throws Exception {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setUserName(null);
        requestDto.setFirstName("Azriel");
        requestDto.setLastName("Fauzi");
        requestDto.setEmail("azrielfhr2@gmail.com");
        requestDto.setPassword("rahasia");

        mockMvc.perform(
                post("/api/v1/user/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void registerUserAlreadyExist() throws Exception {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setUserName("azriel");
        requestDto.setFirstName("Azriel");
        requestDto.setLastName("Fauzi");
        requestDto.setEmail("azrielfhr2@gmail.com");
        requestDto.setPassword("rahasia");

        mockMvc.perform(
                post("/api/v1/user/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerUserSuccess() throws Exception {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setUserName(faker.name().username());
        requestDto.setFirstName(faker.name().firstName());
        requestDto.setLastName(faker.name().lastName());
        requestDto.setEmail(faker.internet().emailAddress());
        requestDto.setPassword("password");

        mockMvc.perform(
                post("/api/v1/user/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    UserResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);

                    assertNotNull(response.token());
                });
    }

    @Test
    void loginSuccess() throws Exception {
        UserRequestLogin request = new UserRequestLogin("jolynn.klocko@yahoo.com", "password");

        mockMvc.perform(
                post("/api/v1/user/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    UserResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);

                    assertNotNull(response.token());
                });
    }
}