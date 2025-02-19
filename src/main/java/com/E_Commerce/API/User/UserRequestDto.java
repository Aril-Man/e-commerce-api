package com.E_Commerce.API.User;

import lombok.Data;

@Data
public class UserRequestDto {
    String firstName;
    String lastName;
    String userName;
    String email;
    String password;
}
