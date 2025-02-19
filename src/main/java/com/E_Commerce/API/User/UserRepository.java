package com.E_Commerce.API.User;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, String> {

    UserModel findByUserName(String userName);

    UserModel findByEmail(String email);

}
