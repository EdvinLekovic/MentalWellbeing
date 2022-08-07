package com.example.backend.models.requests.user_requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EditUserRequest {
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

    public EditUserRequest(String username,
                           String name,
                           String lastName,
                           String password,
                           String repeatPassword) {
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }
}
