package com.example.backend.controllers;

import com.example.backend.models.Video;
import com.example.backend.models.requests.user_requests.ProductUserRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;
import com.example.backend.models.requests.video_requests.AddVideoRequest;
import com.example.backend.models.requests.video_requests.EditVideoRequest;
import com.example.backend.models.response.MultimediaMetadataResponse;
import com.example.backend.services.VideoService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
@CrossOrigin("http://localhost:4200")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public List<MultimediaMetadataResponse> getAllVideos(){
        return videoService.allVideos();
    }

    @GetMapping("/{id}")
    public Video getVideoById(@PathVariable Long id){
        return videoService.getVideoById(id);
    }

    @GetMapping("/metadata/{id}")
    public MultimediaMetadataResponse getVideoMetadata(@PathVariable Long id){
        return videoService.getVideoMetadata(id);
    }

    @PostMapping("/check-video-own")
    public boolean checkIfUserOwnVideo(@RequestBody ProductUserRequest productUserRequest){
        return this.videoService.checkIfUserOwnTheVideo(productUserRequest);
    }

    @GetMapping("/videos-by-user")
    public List<Video> getAllVideosByUsersUsername(@RequestBody UsernameRequest usernameRequest){
        return videoService.allVideosByUsersUsername(usernameRequest);
    }

    @PostMapping(value = "/add-new-video",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Optional<Video> addNewVideo(@RequestParam MultipartFile video,
                                       @RequestParam String title,
                                       @RequestParam String description,
                                       @RequestParam String price,
                                       @RequestParam String creatorUsername,
                                       @RequestParam MultipartFile image){
        AddVideoRequest addVideoRequest = new AddVideoRequest(video,title,description,price,creatorUsername,image);
        return videoService.addVideo(addVideoRequest);
    }

    @PostMapping("/edit-video")
    public Optional<Video> editVideo(@RequestBody EditVideoRequest editVideoRequest){
        return videoService.editVideo(editVideoRequest);
    }

    @DeleteMapping("/delete-video/{videoId}")
    public void deleteVideo(@PathVariable Long videoId){
        videoService.deleteVideo(videoId);
    }
}
