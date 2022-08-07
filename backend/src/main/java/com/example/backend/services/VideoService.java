package com.example.backend.services;

import com.example.backend.models.Video;
import com.example.backend.models.requests.user_requests.ProductUserRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;
import com.example.backend.models.requests.video_requests.AddVideoRequest;
import com.example.backend.models.requests.video_requests.EditVideoRequest;
import com.example.backend.models.response.MultimediaMetadataResponse;

import java.util.List;
import java.util.Optional;

public interface VideoService {
    List<MultimediaMetadataResponse> allVideos();
    Video getVideoById(Long id);
    boolean checkIfUserOwnTheVideo(ProductUserRequest productUserRequest);
    MultimediaMetadataResponse getVideoMetadata(Long id);
    List<Video> allVideosByUsersUsername(UsernameRequest usernameRequest);
    Optional<Video> addVideo(AddVideoRequest addVideoRequest);
    Optional<Video> editVideo(EditVideoRequest editVideoRequest);
    void deleteVideo(Long videoId);
}
