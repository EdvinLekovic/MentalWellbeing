package com.example.backend.models.requests.video_requests;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
public class AddVideoRequest {
    @NotNull
    private MultipartFile video;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String price;
    @NotNull
    private String usernameRequest;
    private MultipartFile image;

    public AddVideoRequest(MultipartFile video,
                           String title,
                           String description,
                           String price,
                           String usernameRequest,
                           MultipartFile image) {
        this.video = video;
        this.title = title;
        this.description = description;
        this.price = price;
        this.usernameRequest = usernameRequest;
        this.image = image;
    }

    public AddVideoRequest() {
    }
}
