package com.example.backend.models.requests.user_requests;

import lombok.Data;
import lombok.Getter;

@Data
public class AuthRequest {
    private String username;
    private String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthRequest() {
    }
}
