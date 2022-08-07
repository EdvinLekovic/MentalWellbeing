package com.example.backend.services;

import com.example.backend.models.ReviewVideo;
import com.example.backend.models.requests.review_requests.AddReviewRequest;
import com.example.backend.models.requests.review_requests.EditReviewRequest;
import com.example.backend.models.response.ReviewResponse;

import java.util.List;

public interface ReviewVideoService {

    List<ReviewResponse> getAllVideoReviews(Long id);
    Double averageVideoReviewsStars(Long id);
    ReviewVideo addVideoReview(AddReviewRequest addReviewRequest);
    ReviewVideo editVideoReview(EditReviewRequest editReviewRequest);
    void deleteReview(Long id);
}
