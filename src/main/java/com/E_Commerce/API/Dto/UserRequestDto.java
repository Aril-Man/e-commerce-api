package com.E_Commerce.API.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    String firstName;
    String lastName;
    @NotBlank
    String userName;
    @NotBlank
    String email;
    @NotBlank
    String password;
}
