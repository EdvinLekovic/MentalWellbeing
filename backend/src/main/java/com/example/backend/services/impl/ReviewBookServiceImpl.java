package com.example.backend.services.impl;

import com.example.backend.models.Book;
import com.example.backend.models.ReviewBook;
import com.example.backend.models.User;
import com.example.backend.models.requests.review_requests.AddReviewRequest;
import com.example.backend.models.requests.review_requests.EditReviewRequest;
import com.example.backend.models.response.ReviewResponse;
import com.example.backend.repositories.BookRepository;
import com.example.backend.repositories.ReviewBookRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.ReviewBookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewBookServiceImpl implements ReviewBookService {
    private final ReviewBookRepository reviewBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReviewBookServiceImpl(ReviewBookRepository reviewBookRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.reviewBookRepository = reviewBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<ReviewResponse> getAllReviewsByBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();
        return reviewBookRepository.findAllByBook(book).stream().map(reviewVideo -> new ReviewResponse(reviewVideo.getDescription(),reviewVideo.getUser(),reviewVideo.getRating())).toList();
    }

    @Override
    public Double averageBookReviewsStars(Long id) {
        boolean averagePresent = getAllReviewsByBook(id).stream().mapToDouble(ReviewResponse::getRating).average().isPresent();
        if(averagePresent){
            return getAllReviewsByBook(id).stream().mapToDouble(ReviewResponse::getRating).average().getAsDouble();
        }
        return 0.0;
    }

    @Override
    public ReviewBook addBookReview(AddReviewRequest addReviewRequest) {
        User user = this.userRepository.findById(addReviewRequest.getUsername()).orElseThrow();
        Book book = this.bookRepository.findById(addReviewRequest.getMediaId()).orElseThrow();
        return this.reviewBookRepository.save(new ReviewBook(addReviewRequest.getDescription(),
                addReviewRequest.getRating(),
                book,
                user));
    }

    @Override
    public ReviewBook editBookReview(EditReviewRequest editReviewRequest) {
        ReviewBook review = this.reviewBookRepository.findById(editReviewRequest.getId()).orElseThrow();
        if(!editReviewRequest.getDescription().isEmpty()){
            review.setDescription(editReviewRequest.getDescription());
        }
        if(editReviewRequest.getRating() == null){
            review.setRating(editReviewRequest.getRating());
        }
        return this.reviewBookRepository.save(review);
    }

    @Override
    public void deleteBookReview(Long id) {
        this.reviewBookRepository.deleteById(id);
    }
}
