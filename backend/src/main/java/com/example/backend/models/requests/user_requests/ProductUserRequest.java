package com.example.backend.models.requests.user_requests;

import lombok.Data;

@Data
public class ProductUserRequest {
    private Long productId;
    private String username;

    public ProductUserRequest(Long productId, String username) {
        this.productId = productId;
        this.username = username;
    }

    public ProductUserRequest() {
    }
}
