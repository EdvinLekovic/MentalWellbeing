package com.example.backend.services.impl;

import com.example.backend.models.ReviewVideo;
import com.example.backend.models.User;
import com.example.backend.models.Video;
import com.example.backend.models.requests.review_requests.AddReviewRequest;
import com.example.backend.models.requests.review_requests.EditReviewRequest;
import com.example.backend.models.response.ReviewResponse;
import com.example.backend.repositories.ReviewVideoRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.repositories.VideoRepository;
import com.example.backend.services.ReviewVideoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewVideoServiceImpl implements ReviewVideoService {

    private final ReviewVideoRepository reviewVideoRepository;
    private final UserRepository userRepository;

    private final VideoRepository videoRepository;

    public ReviewVideoServiceImpl(ReviewVideoRepository reviewVideoRepository, UserRepository userRepository, VideoRepository videoRepository) {
        this.reviewVideoRepository = reviewVideoRepository;
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
    }

    @Override
    public List<ReviewResponse> getAllVideoReviews(Long id) {
        Video video = videoRepository.findById(id).orElseThrow();
        List<ReviewResponse> reviewVideos = reviewVideoRepository.findReviewVideoByVideo(video).stream().map(reviewVideo -> new ReviewResponse(reviewVideo.getDescription(),reviewVideo.getUser(),reviewVideo.getRating())).toList();
        return reviewVideos;
    }

    @Override
    public Double averageVideoReviewsStars(Long id) {
        boolean averagePresent = getAllVideoReviews(id).stream().mapToDouble(ReviewResponse::getRating).average().isPresent();
        if(averagePresent){
            return getAllVideoReviews(id).stream().mapToDouble(ReviewResponse::getRating).average().getAsDouble();
        }
        return 0.0;
    }

    @Override
    public ReviewVideo addVideoReview(AddReviewRequest addReviewRequest) {
        User user = this.userRepository.findById(addReviewRequest.getUsername()).orElseThrow();
        Video video = this.videoRepository.findById(addReviewRequest.getMediaId()).orElseThrow();
        return this.reviewVideoRepository.save(new ReviewVideo(addReviewRequest.getDescription(),
                addReviewRequest.getRating(),
                video,
                user));
    }

    @Override
    public ReviewVideo editVideoReview(EditReviewRequest editReviewRequest) {
        ReviewVideo review = this.reviewVideoRepository.findById(editReviewRequest.getId()).orElseThrow();
        if(!editReviewRequest.getDescription().isEmpty()){
            review.setDescription(editReviewRequest.getDescription());
        }
        if(editReviewRequest.getRating() == null){
            review.setRating(editReviewRequest.getRating());
        }
        return this.reviewVideoRepository.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        this.reviewVideoRepository.deleteById(id);
    }
}
