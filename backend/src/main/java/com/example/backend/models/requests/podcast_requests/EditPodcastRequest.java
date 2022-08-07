package com.example.backend.models.requests.podcast_requests;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class EditPodcastRequest {
    @NotNull
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;

    public EditPodcastRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public EditPodcastRequest() {
    }
}
