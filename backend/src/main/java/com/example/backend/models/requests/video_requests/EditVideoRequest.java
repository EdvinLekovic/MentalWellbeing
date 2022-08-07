package com.example.backend.models.requests.video_requests;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class EditVideoRequest {

    @NotNull
    private Long id;
    private String title;
    private String description;
    private String price;
    private String creatorUsername;

    public EditVideoRequest(String title,
                            String description,
                            String price,
                            String creatorUsername) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.creatorUsername = creatorUsername;
    }

    public EditVideoRequest() {
    }
}
