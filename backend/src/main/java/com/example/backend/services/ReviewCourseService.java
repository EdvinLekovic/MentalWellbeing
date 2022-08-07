package com.example.backend.services;

import com.example.backend.models.ReviewCourse;
import com.example.backend.models.ReviewPodcast;
import com.example.backend.models.requests.review_requests.AddReviewRequest;
import com.example.backend.models.requests.review_requests.EditReviewRequest;
import com.example.backend.models.response.ReviewResponse;

import java.util.List;

public interface ReviewCourseService {
    List<ReviewResponse> getAllVideoCourseReviews(Long id);
    Double averageVideoCourseReviewsStars(Long id);
    ReviewCourse addVideoCourseReview(AddReviewRequest addReviewRequest);
    ReviewCourse editVideoCourseReview(EditReviewRequest editReviewRequest);
    void deleteReview(Long id);
}
