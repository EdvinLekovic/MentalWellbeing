package com.example.backend.services;

import com.example.backend.models.User;
import com.example.backend.models.requests.user_requests.EditUserRequest;
import com.example.backend.models.requests.user_requests.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> register(UserRequest userRequest);
    Optional<User> findUserByUsername(String username);
    Optional<User> editUserInformation(EditUserRequest editUserRequest);
}
