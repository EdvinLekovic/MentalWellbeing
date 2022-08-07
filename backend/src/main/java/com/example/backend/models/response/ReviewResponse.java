package com.example.backend.models.response;

import com.example.backend.models.User;
import lombok.Data;

@Data
public class ReviewResponse {
    private String description;
    private User user;
    private Long rating;

    public ReviewResponse(String description, User user, Long rating) {
        this.description = description;
        this.user = user;
        this.rating = rating;
    }

    public ReviewResponse() {
    }
}
