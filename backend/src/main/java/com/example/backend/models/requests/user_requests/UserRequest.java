package com.example.backend.models.requests.user_requests;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.example.backend.models.Image;
import com.example.backend.models.enums.Role;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserRequest {
    @NotNull
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private String password;
    @NotNull
    private String repeatPassword;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public UserRequest(String username, String name, String lastName, String password, String repeatPassword, Role role) {
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.role = role;
    }

    public UserRequest() {
    }
}
