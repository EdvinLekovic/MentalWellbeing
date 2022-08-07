package com.example.backend.services.impl;

import com.example.backend.models.Podcast;
import com.example.backend.models.ReviewPodcast;
import com.example.backend.models.User;
import com.example.backend.models.requests.review_requests.AddReviewRequest;
import com.example.backend.models.requests.review_requests.EditReviewRequest;
import com.example.backend.models.response.ReviewResponse;
import com.example.backend.repositories.PodcastRepository;
import com.example.backend.repositories.ReviewPodcastRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.ReviewPodcastService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewPodcastServiceImpl implements ReviewPodcastService {

    private final ReviewPodcastRepository reviewPodcastRepository;
    private final UserRepository userRepository;
    private final PodcastRepository podcastRepository;

    public ReviewPodcastServiceImpl(ReviewPodcastRepository reviewPodcastRepository, UserRepository userRepository, PodcastRepository podcastRepository) {
        this.reviewPodcastRepository = reviewPodcastRepository;
        this.userRepository = userRepository;
        this.podcastRepository = podcastRepository;
    }

    @Override
    public List<ReviewResponse> getAllPodcastReviews(Long id) {
        Podcast podcast = podcastRepository.findById(id).orElseThrow();
        return reviewPodcastRepository.findAllByPodcast(podcast).stream().map(reviewVideo -> new ReviewResponse(reviewVideo.getDescription(),reviewVideo.getUser(),reviewVideo.getRating())).toList();
    }

    @Override
    public Double averagePodcastReviewsStars(Long id) {
        boolean averagePresent = getAllPodcastReviews(id).stream().mapToDouble(ReviewResponse::getRating).average().isPresent();
        if(averagePresent){
            return getAllPodcastReviews(id).stream().mapToDouble(ReviewResponse::getRating).average().getAsDouble();
        }
        return 0.0;
    }

    @Override
    public ReviewPodcast addPodcastReview(AddReviewRequest addReviewRequest) {
        User user = this.userRepository.findById(addReviewRequest.getUsername()).orElseThrow();
        Podcast podcast = this.podcastRepository.findById(addReviewRequest.getMediaId()).orElseThrow();
        return this.reviewPodcastRepository.save(new ReviewPodcast(addReviewRequest.getDescription(),
                addReviewRequest.getRating(),
                podcast,
                user));
    }

    @Override
    public ReviewPodcast editPodcastReview(EditReviewRequest editReviewRequest) {
        ReviewPodcast review = this.reviewPodcastRepository.findById(editReviewRequest.getId()).orElseThrow();
        if(!editReviewRequest.getDescription().isEmpty()){
            review.setDescription(editReviewRequest.getDescription());
        }
        if(editReviewRequest.getRating() == null){
            review.setRating(editReviewRequest.getRating());
        }
        return this.reviewPodcastRepository.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        this.reviewPodcastRepository.deleteById(id);
    }
}
