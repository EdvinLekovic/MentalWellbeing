package com.example.backend.models.requests.review_requests;

import lombok.Data;

@Data
public class EditReviewRequest {
    private Long id;
    private String description;
    private Long rating;

    public EditReviewRequest(Long id, String description, Long rating) {
        this.id = id;
        this.description = description;
        this.rating = rating;
    }

    public EditReviewRequest() {
    }
}
