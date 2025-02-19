package com.E_Commerce.API.User;

public record UserResponse(
                String firstName,
                String lastName,
                String userName,
                String email,
                String token) {

}
