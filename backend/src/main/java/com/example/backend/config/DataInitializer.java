package com.example.backend.config;

import com.example.backend.models.User;
import com.example.backend.models.enums.Role;
import com.example.backend.models.requests.user_requests.UserRequest;
import com.example.backend.services.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class DataInitializer {

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void initAdmins() {
        Optional<User> user1 = userService.findUserByUsername("edvin12");
        if (user1.isEmpty()) {
            UserRequest userDto1 = new UserRequest("edvin12",
                    "Edvin",
                    "Lekovic",
                    "edvin",
                    "edvin",
                    Role.ADMIN);
            this.userService.register(userDto1);
        }
    }
}