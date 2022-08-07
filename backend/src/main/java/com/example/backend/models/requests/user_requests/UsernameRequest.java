package com.example.backend.models.requests.user_requests;

import lombok.Data;

@Data
public class UsernameRequest {
    private String username;

    public UsernameRequest(String username) {
        this.username = username;
    }

    public UsernameRequest() {
    }
}
