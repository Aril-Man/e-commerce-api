package com.E_Commerce.API.Repository;


import com.E_Commerce.API.Entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserModel, String> {

    UserModel findByUserName(String userName);

    UserModel findByEmail(String email);

}
