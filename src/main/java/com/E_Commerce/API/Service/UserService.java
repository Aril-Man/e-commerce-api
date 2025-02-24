package com.E_Commerce.API.Service;

import com.E_Commerce.API.Dto.UserRequestDto;
import com.E_Commerce.API.Dto.UserRequestLogin;
import com.E_Commerce.API.Dto.UserResponse;
import com.E_Commerce.API.Entity.UserModel;
import com.E_Commerce.API.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.E_Commerce.API.Authentication.JwtUtils;
import com.E_Commerce.API.Entity.CartModel;
import com.E_Commerce.API.Repository.CartRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public UserResponse registerUser(UserRequestDto request) {
        // Check if user already exists
        UserModel userEmail = userRepository.findByEmail(request.getEmail());
        UserModel userUserName = userRepository.findByUserName(request.getUserName());
        if (userEmail != null || userUserName != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }

        // Create a new user
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserModel user = UserModel
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(encodedPassword)
                .build();
        userRepository.save(user);

        // Create a cart for the user
        CartModel cart = CartModel
                .builder()
                .user(user)
                .build();
        cartRepository.save(cart);
        
        // Return user details and token on successful registration
        String jwtToken = jwtUtils.generateToken(request.getEmail());
        return new UserResponse(user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getEmail(),
                jwtToken);
    }

    public UserResponse login(UserRequestLogin request) {
        String email = request.email();
        String password = (request.password());
        UserModel user = userRepository.findByEmail(email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        String jwtToken = jwtUtils.generateToken(email);

        // Return user details and token on successful login
        return new UserResponse(user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getEmail(),
                jwtToken);
    }

    
}
