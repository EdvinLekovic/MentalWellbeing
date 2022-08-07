package com.example.backend.services;

import com.example.backend.models.ReviewBook;
import com.example.backend.models.requests.review_requests.AddReviewRequest;
import com.example.backend.models.requests.review_requests.EditReviewRequest;
import com.example.backend.models.response.ReviewResponse;

import java.util.List;

public interface ReviewBookService {

    List<ReviewResponse> getAllReviewsByBook(Long id);
    Double averageBookReviewsStars(Long id);
    ReviewBook addBookReview(AddReviewRequest addReviewRequest);
    ReviewBook editBookReview(EditReviewRequest editReviewRequest);
    void deleteBookReview(Long id);
}
