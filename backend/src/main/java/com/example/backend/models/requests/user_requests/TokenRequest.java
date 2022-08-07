package com.example.backend.models.requests.user_requests;

import lombok.Getter;

@Getter
public class TokenRequest {
    private String token;

    public TokenRequest(String token) {
        this.token = token;
    }

    public TokenRequest() {
    }
}
