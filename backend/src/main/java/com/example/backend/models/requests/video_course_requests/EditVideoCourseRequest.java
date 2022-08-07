package com.example.backend.models.requests.video_course_requests;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
public class EditVideoCourseRequest {
    @NotNull
    private Long id;
    private String title;
    private String description;
    private MultipartFile image;

    public EditVideoCourseRequest(Long id,
                                  String title,
                                  String description,
                                  MultipartFile image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public EditVideoCourseRequest() {
    }
}
