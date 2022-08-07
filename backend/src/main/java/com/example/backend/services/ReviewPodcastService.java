package com.example.backend.services;

import com.example.backend.models.ReviewPodcast;
import com.example.backend.models.requests.review_requests.AddReviewRequest;
import com.example.backend.models.requests.review_requests.EditReviewRequest;
import com.example.backend.models.response.ReviewResponse;

import java.util.List;

public interface ReviewPodcastService {

    List<ReviewResponse> getAllPodcastReviews(Long id);
    Double averagePodcastReviewsStars(Long id);
    ReviewPodcast addPodcastReview(AddReviewRequest addReviewRequest);
    ReviewPodcast editPodcastReview(EditReviewRequest editReviewRequest);
    void deleteReview(Long id);
}
