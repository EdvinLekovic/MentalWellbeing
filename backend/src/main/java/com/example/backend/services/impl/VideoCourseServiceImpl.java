package com.example.backend.services.impl;

import com.example.backend.models.*;
import com.example.backend.models.requests.user_requests.ProductUserRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;
import com.example.backend.models.requests.video_course_requests.AddVideoCourseRequest;
import com.example.backend.models.requests.video_course_requests.AddVideoToVideoCourseRequest;
import com.example.backend.models.requests.video_course_requests.EditVideoCourseRequest;
import com.example.backend.models.response.MultimediaMetadataResponse;
import com.example.backend.repositories.ImageRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.repositories.VideoCourseRepository;
import com.example.backend.repositories.VideoRepository;
import com.example.backend.services.ReviewCourseService;
import com.example.backend.services.VideoCourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoCourseServiceImpl implements VideoCourseService {
    private final VideoCourseRepository videoCourseRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ReviewCourseService reviewCourseService;

    public VideoCourseServiceImpl(VideoCourseRepository videoCourseRepository,
                                  VideoRepository videoRepository, UserRepository userRepository, ImageRepository imageRepository, ReviewCourseService reviewCourseService) {
        this.videoCourseRepository = videoCourseRepository;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.reviewCourseService = reviewCourseService;
    }

    @Override
    public List<MultimediaMetadataResponse> listAllVideoCourses() {
        return videoCourseRepository.findAll().stream()
                .map(course -> new MultimediaMetadataResponse(course.getId(),
                        course.getName(),
                        course.getDescription(),
                        course.getPrice(),
                        course.getUsers().size(),
                        course.getImage(),
                        course.getCreator().getFullName(),
                        reviewCourseService.averageVideoCourseReviewsStars(course.getId()))).toList();
    }

    @Override
    public VideoCourse getVideoCourseById(Long id) {
        return videoCourseRepository.findById(id).orElseThrow();
    }

    @Override
    public boolean checkIfUserOwnTheCourse(ProductUserRequest productUserRequest) {
        VideoCourse videoCourse = videoCourseRepository.findById(productUserRequest.getProductId()).orElseThrow();
        return videoCourse
                .getUsers()
                .stream()
                .anyMatch(user -> productUserRequest.getUsername().equals(user.getUsername())) ||
                videoCourse.getCreator().getUsername().equals(productUserRequest.getUsername());
    }

    @Override
    public MultimediaMetadataResponse getVideoCourseMetadata(Long id) {
        return videoCourseRepository.findById(id).map(videoCourse -> new MultimediaMetadataResponse(videoCourse.getId(),
                videoCourse.getName(),
                videoCourse.getDescription(),
                videoCourse.getPrice(),
                videoCourse.getUsers().size(),
                videoCourse.getImage(),
                videoCourse.getCreator().getFullName(),
                reviewCourseService.averageVideoCourseReviewsStars(videoCourse.getId()))).orElseThrow();
    }

    @Override
    public List<VideoCourse> allVideoCoursesByUsersUsername(UsernameRequest usernameRequest) {
        return videoCourseRepository.findAll()
                .stream()
                .filter(videoCourse -> videoCourse.getUsers()
                        .stream()
                        .anyMatch(user -> user.getUsername().equals(usernameRequest.getUsername())))
                .toList();
    }

    @Override
    public List<Video> listAllVideosByCourse(Long videoCourseId) {
        VideoCourse videoCourse = videoCourseRepository.findById(videoCourseId).orElseThrow();
        return videoRepository.findAllByVideoCourse(videoCourse);
    }

    @Override
    public Optional<VideoCourse> addVideoCourse(AddVideoCourseRequest addVideoCourseRequest) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            UsernameRequest usernameRequest = mapper.readValue(addVideoCourseRequest.getCreatorUsername(), UsernameRequest.class);
            User creator = userRepository.findById(usernameRequest.getUsername()).orElseThrow();
            Image image = new Image(addVideoCourseRequest.getImage().getOriginalFilename(),
                    addVideoCourseRequest.getImage().getContentType(),
                    addVideoCourseRequest.getImage().getBytes());
            imageRepository.save(image);
            Double price = mapper.readValue(addVideoCourseRequest.getPrice(), Double.class);
            return Optional.of(videoCourseRepository.save(new VideoCourse(addVideoCourseRequest.getTitle(),
                    addVideoCourseRequest.getDescription(),
                    price,
                    creator,
                    image)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<VideoCourse> editVideoCourse(EditVideoCourseRequest editVideoCourseRequest) {
        VideoCourse videoCourse = videoCourseRepository.findById(editVideoCourseRequest.getId()).orElseThrow();
        if (!editVideoCourseRequest.getTitle().isEmpty()) {
            videoCourse.setName(editVideoCourseRequest.getTitle());
        }
        if (!editVideoCourseRequest.getDescription().isEmpty()) {
            videoCourse.setDescription(editVideoCourseRequest.getDescription());
        }
        return Optional.of(videoCourseRepository.save(videoCourse));
    }

    @Override
    public void deleteVideoCourse(Long videoCourseId) {
        videoCourseRepository.deleteById(videoCourseId);
    }

    @Override
    public List<Video> addVideosToVideoCourse(AddVideoToVideoCourseRequest addVideoToVideoCourseRequest) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Long videoCourseId = mapper.readValue(addVideoToVideoCourseRequest.getVideoCourseId(), Long.class);
            VideoCourse videoCourse = videoCourseRepository
                    .findById(videoCourseId)
                    .orElseThrow();
            List<Video> videos = new ArrayList<>();
            for (MultipartFile videoFile : addVideoToVideoCourseRequest.getVideos()) {
                Video video = new Video(videoFile.getBytes(),
                        addVideoToVideoCourseRequest.getTitle(),
                        addVideoToVideoCourseRequest.getDescription(),
                        videoCourse);
                videos.add(video);
            }
            return videoRepository.saveAll(videos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
