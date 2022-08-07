package com.example.backend.controllers;

import com.example.backend.models.Video;
import com.example.backend.models.VideoCourse;
import com.example.backend.models.requests.user_requests.ProductUserRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;
import com.example.backend.models.requests.video_course_requests.AddVideoCourseRequest;
import com.example.backend.models.requests.video_course_requests.AddVideoToVideoCourseRequest;
import com.example.backend.models.requests.video_course_requests.EditVideoCourseRequest;
import com.example.backend.models.response.MultimediaMetadataResponse;
import com.example.backend.services.VideoCourseService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/video-courses")
@CrossOrigin("http://localhost:4200")
public class VideoCourseController {

    private final VideoCourseService videoCourseService;

    public VideoCourseController(VideoCourseService videoCourseService) {
        this.videoCourseService = videoCourseService;
    }

    @GetMapping
    public List<MultimediaMetadataResponse> getAllVideoCourses(){
        return videoCourseService.listAllVideoCourses();
    }

    @GetMapping("/{id}")
    public VideoCourse getVideoCourseById(@PathVariable Long id){
        return videoCourseService.getVideoCourseById(id);
    }

    @GetMapping("/metadata/{id}")
    public MultimediaMetadataResponse getVideoCourseMetadata(@PathVariable Long id){
        return this.videoCourseService.getVideoCourseMetadata(id);
    }

    @PostMapping("/check-course-own")
    public boolean checkIfUserOwnCourse(@RequestBody ProductUserRequest productUserRequest){
        return this.videoCourseService.checkIfUserOwnTheCourse(productUserRequest);
    }

    @GetMapping("/videos-by-user")
    public List<VideoCourse> allVideoCoursesByUsersUsername(UsernameRequest usernameRequest){
        return videoCourseService.allVideoCoursesByUsersUsername(usernameRequest);
    }

    @GetMapping("/videos-by-course/{videoCourseId}")
    public List<Video> listAllVideosByCourse(@PathVariable Long videoCourseId){
        return videoCourseService.listAllVideosByCourse(videoCourseId);
    }

    @PostMapping(value = "/add-new-video-course",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Optional<VideoCourse> addVideoCourse(@RequestParam String title,
                                                @RequestParam String description,
                                                @RequestParam String price,
                                                @RequestParam String creatorUsername,
                                                @RequestParam MultipartFile image) throws IOException {
        AddVideoCourseRequest addVideoCourseRequest = new AddVideoCourseRequest(title,description,price,creatorUsername,image);
        return videoCourseService.addVideoCourse(addVideoCourseRequest);
    }

    @PostMapping("/edit-video-course")
    public Optional<VideoCourse> editVideoCourse(@RequestBody EditVideoCourseRequest editVideoCourseRequest){
        return videoCourseService.editVideoCourse(editVideoCourseRequest);
    }

    @PostMapping(value = "/add-videos-to-video-course",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Video> addVideosToVideoCourse(@RequestParam String videoCourseId,
                                              @RequestParam MultipartFile[] videos,
                                              @RequestParam String title,
                                              @RequestParam String description){
        AddVideoToVideoCourseRequest addVideoToVideoCourseRequest = new AddVideoToVideoCourseRequest(videoCourseId,videos,title,description);
        return videoCourseService.addVideosToVideoCourse(addVideoToVideoCourseRequest);
    }

    @DeleteMapping("/delete-video-course/{videoCourseId}")
    public void deleteVideoCourse(@PathVariable Long videoCourseId){
        videoCourseService.deleteVideoCourse(videoCourseId);
    }
}
