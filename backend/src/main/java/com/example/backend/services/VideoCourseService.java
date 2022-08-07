package com.example.backend.services;

import com.example.backend.models.Video;
import com.example.backend.models.VideoCourse;
import com.example.backend.models.requests.user_requests.ProductUserRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;
import com.example.backend.models.requests.video_course_requests.AddVideoCourseRequest;
import com.example.backend.models.requests.video_course_requests.AddVideoToVideoCourseRequest;
import com.example.backend.models.requests.video_course_requests.EditVideoCourseRequest;
import com.example.backend.models.response.MultimediaMetadataResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface VideoCourseService {

    List<MultimediaMetadataResponse> listAllVideoCourses();
    VideoCourse getVideoCourseById(Long id);
    boolean checkIfUserOwnTheCourse(ProductUserRequest productUserRequest);
    MultimediaMetadataResponse getVideoCourseMetadata(Long id);
    List<VideoCourse> allVideoCoursesByUsersUsername(UsernameRequest usernameRequest);
    List<Video> listAllVideosByCourse(Long videoCourseId);
    Optional<VideoCourse> addVideoCourse(AddVideoCourseRequest addVideoCourseRequest) throws IOException;
    Optional<VideoCourse> editVideoCourse(EditVideoCourseRequest editVideoCourseRequestRequest);
    void deleteVideoCourse(Long videoCourseId);
    List<Video> addVideosToVideoCourse(AddVideoToVideoCourseRequest addVideoToVideoCourseRequest);
}
