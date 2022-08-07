package com.example.backend.models.requests.video_course_requests;

import com.example.backend.models.Image;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
public class AddVideoCourseRequest {
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String price;
    @NotNull
    private String creatorUsername;
    private MultipartFile image;

    public AddVideoCourseRequest(String title,
                                 String description,
                                 String price,
                                 String creatorUsername,
                                 MultipartFile image) {
        this.title = title;
        this.description = description;
        this.creatorUsername = creatorUsername;
        this.price = price;
        this.image = image;
    }

    public AddVideoCourseRequest() {
    }
}
