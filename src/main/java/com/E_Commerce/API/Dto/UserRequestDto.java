package com.E_Commerce.API.Dto;

import lombok.Data;

@Data
public class UserRequestDto {
    String firstName;
    String lastName;
    String userName;
    String email;
    String password;
}
