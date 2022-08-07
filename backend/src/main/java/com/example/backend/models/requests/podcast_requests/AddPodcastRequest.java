package com.example.backend.models.requests.podcast_requests;

import com.example.backend.models.User;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
public class AddPodcastRequest {
    @NotNull
    private MultipartFile podcast;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String price;
    @NotNull
    private String creatorUsername;
    private MultipartFile image;

    public AddPodcastRequest(MultipartFile podcast,
                             String title,
                             String description,
                             String price,
                             String creatorUsername,
                             MultipartFile image) {
        this.podcast = podcast;
        this.title = title;
        this.description = description;
        this.price = price;
        this.creatorUsername = creatorUsername;
        this.image = image;
    }

    public AddPodcastRequest() {
    }
}
