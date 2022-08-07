package com.example.backend.services.impl;

import com.example.backend.models.*;
import com.example.backend.models.requests.review_requests.AddReviewRequest;
import com.example.backend.models.requests.review_requests.EditReviewRequest;
import com.example.backend.models.response.ReviewResponse;
import com.example.backend.repositories.ReviewCourseRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.repositories.VideoCourseRepository;
import com.example.backend.services.ReviewCourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewCourseServiceImpl implements ReviewCourseService {

    private final ReviewCourseRepository reviewCourseRepository;
    private final VideoCourseRepository videoCourseRepository;
    private final UserRepository userRepository;

    public ReviewCourseServiceImpl(ReviewCourseRepository reviewCourseRepository,
                                   VideoCourseRepository videoCourseRepository, UserRepository userRepository) {
        this.reviewCourseRepository = reviewCourseRepository;
        this.videoCourseRepository = videoCourseRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<ReviewResponse> getAllVideoCourseReviews(Long id) {
        VideoCourse videoCourse = videoCourseRepository.findById(id).orElseThrow();
        List<ReviewResponse> reviewVideos = reviewCourseRepository.findAllByVideoCourse(videoCourse).stream().map(reviewVideo -> new ReviewResponse(reviewVideo.getDescription(),reviewVideo.getUser(),reviewVideo.getRating())).toList();
        return reviewVideos;
    }

    @Override
    public Double averageVideoCourseReviewsStars(Long id) {
        boolean averagePresent = getAllVideoCourseReviews(id).stream().mapToDouble(ReviewResponse::getRating).average().isPresent();
        if(averagePresent){
            return getAllVideoCourseReviews(id).stream().mapToDouble(ReviewResponse::getRating).average().getAsDouble();
        }
        return 0.0;
    }

    @Override
    public ReviewCourse addVideoCourseReview(AddReviewRequest addReviewRequest) {
        User user = this.userRepository.findById(addReviewRequest.getUsername()).orElseThrow();
        VideoCourse videoCourse = this.videoCourseRepository.findById(addReviewRequest.getMediaId()).orElseThrow();
        return this.reviewCourseRepository.save(new ReviewCourse(addReviewRequest.getDescription(),
                addReviewRequest.getRating(),
                videoCourse,
                user));
    }

    @Override
    public ReviewCourse editVideoCourseReview(EditReviewRequest editReviewRequest) {
        ReviewCourse review = this.reviewCourseRepository.findById(editReviewRequest.getId()).orElseThrow();
        if(!editReviewRequest.getDescription().isEmpty()){
            review.setDescription(editReviewRequest.getDescription());
        }
        if(editReviewRequest.getRating() == null){
            review.setRating(editReviewRequest.getRating());
        }
        return this.reviewCourseRepository.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        this.reviewCourseRepository.deleteById(id);
    }
}
