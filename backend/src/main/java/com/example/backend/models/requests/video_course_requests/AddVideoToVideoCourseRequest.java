package com.example.backend.models.requests.video_course_requests;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
public class AddVideoToVideoCourseRequest {
    @NotNull
    private String videoCourseId;
    @NotNull
    private MultipartFile[] videos;
    @NotNull
    private String title;
    @NotNull
    private String description;

    public AddVideoToVideoCourseRequest(String videoCourseId, @NotNull MultipartFile[] videos, String title, String description) {
        this.videoCourseId = videoCourseId;
        this.videos = videos;
        this.title = title;
        this.description = description;
    }

    public AddVideoToVideoCourseRequest() {
    }
}
