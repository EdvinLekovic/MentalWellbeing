package com.example.backend.services.impl;


import com.example.backend.models.Image;
import com.example.backend.models.User;
import com.example.backend.models.Video;
import com.example.backend.models.requests.user_requests.ProductUserRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;
import com.example.backend.models.requests.video_requests.AddVideoRequest;
import com.example.backend.models.requests.video_requests.EditVideoRequest;
import com.example.backend.models.response.MultimediaMetadataResponse;
import com.example.backend.repositories.ImageRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.repositories.VideoRepository;
import com.example.backend.services.ReviewVideoService;
import com.example.backend.services.VideoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ReviewVideoService reviewVideoService;

    public VideoServiceImpl(VideoRepository videoRepository,
                            UserRepository userRepository,
                            ImageRepository imageRepository, ReviewVideoService reviewVideoService) {
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.reviewVideoService = reviewVideoService;
    }

    @Override
    public List<MultimediaMetadataResponse> allVideos() {
        return videoRepository.findAll().stream().filter(video -> video.getCreator() != null)
                .map(video -> new MultimediaMetadataResponse(video.getId(),
                        video.getTitle(),
                        video.getDescription(),
                        video.getPrice(),
                        video.getUsers().size(),
                        video.getImage(),
                        video.getCreator().getFullName(),
                        reviewVideoService.averageVideoReviewsStars(video.getId()))).toList();
    }

    @Override
    public Video getVideoById(Long id) {
        return videoRepository.findById(id).orElseThrow();
    }

    @Override
    public boolean checkIfUserOwnTheVideo(ProductUserRequest productUserRequest) {
        Video video = videoRepository.findById(productUserRequest.getProductId()).orElseThrow();
        return video
                .getUsers()
                .stream()
                .anyMatch(user -> productUserRequest.getUsername().equals(user.getUsername())) ||
                video.getCreator().getUsername().equals(productUserRequest.getUsername());
    }

    @Override
    public MultimediaMetadataResponse getVideoMetadata(Long id) {
        return videoRepository.findById(id).map(video -> new MultimediaMetadataResponse(video.getId(),
                video.getTitle(),
                video.getDescription(),
                video.getPrice(),
                video.getUsers().size(),
                video.getImage(),
                video.getCreator().getFullName(),
                reviewVideoService.averageVideoReviewsStars(video.getId()))).orElseThrow();
    }

    @Override
    public List<Video> allVideosByUsersUsername(UsernameRequest usernameRequest) {
        return videoRepository.findAll()
                .stream()
                .filter(video -> video.getUsers()
                        .stream()
                        .anyMatch(user -> user.getUsername().equals(usernameRequest.getUsername())))
                .toList();
    }

    @Override
    public Optional<Video> addVideo(AddVideoRequest addVideoRequest) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            UsernameRequest usernameRequest = mapper.readValue(addVideoRequest.getUsernameRequest(), UsernameRequest.class);
            User creator = userRepository.findById(usernameRequest.getUsername()).orElseThrow();
            Image image = new Image(addVideoRequest.getImage().getName(), addVideoRequest.getImage().getContentType(), addVideoRequest.getImage().getBytes());
            imageRepository.save(image);
            Double price = mapper.readValue(addVideoRequest.getPrice(), Double.class);
            return Optional.of(videoRepository.save(new Video(addVideoRequest.getVideo().getBytes(),
                    addVideoRequest.getTitle(),
                    addVideoRequest.getDescription(),
                    price,
                    image,
                    creator)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Video> editVideo(EditVideoRequest editVideoRequest) {
        Video video = videoRepository.findById(editVideoRequest.getId()).orElseThrow();
        if (!editVideoRequest.getTitle().isEmpty()) {
            video.setTitle(editVideoRequest.getTitle());
        }
        if (!editVideoRequest.getDescription().isEmpty()) {
            video.setDescription(editVideoRequest.getDescription());
        }
        return Optional.of(videoRepository.save(video));
    }

    @Override
    public void deleteVideo(Long videoId) {
        videoRepository.deleteById(videoId);
    }
}
