package com.example.backend.models.requests.review_requests;

import lombok.Data;

@Data
public class AddReviewRequest {
    private String description;
    private Long rating;
    private Long mediaId;
    private String username;

    public AddReviewRequest(String description, Long rating, Long mediaId, String username) {
        this.description = description;
        this.rating = rating;
        this.mediaId = mediaId;
        this.username = username;
    }

    public AddReviewRequest() {
    }
}
