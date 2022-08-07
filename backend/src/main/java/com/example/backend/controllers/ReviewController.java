package com.example.backend.controllers;

import com.example.backend.models.ReviewBook;
import com.example.backend.models.ReviewCourse;
import com.example.backend.models.ReviewPodcast;
import com.example.backend.models.ReviewVideo;
import com.example.backend.models.requests.review_requests.*;
import com.example.backend.models.response.ReviewResponse;
import com.example.backend.services.ReviewBookService;
import com.example.backend.services.ReviewCourseService;
import com.example.backend.services.ReviewPodcastService;
import com.example.backend.services.ReviewVideoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin("http://localhost:4200")
public class ReviewController {
    private final ReviewVideoService reviewVideoService;
    private final ReviewPodcastService reviewPodcastService;
    private final ReviewBookService reviewBookService;

    private final ReviewCourseService reviewCourseService;

    public ReviewController(ReviewVideoService reviewVideoService,
                            ReviewPodcastService reviewPodcastService,
                            ReviewBookService reviewBookService,
                            ReviewCourseService reviewCourseService) {
        this.reviewVideoService = reviewVideoService;
        this.reviewPodcastService = reviewPodcastService;
        this.reviewBookService = reviewBookService;
        this.reviewCourseService = reviewCourseService;
    }

    @GetMapping("/videos/{id}")
    public List<ReviewResponse> getVideoReviews(@PathVariable Long id){
        return this.reviewVideoService.getAllVideoReviews(id);
    }

    @GetMapping("/podcasts/{id}")
    public List<ReviewResponse> getPodcastReviews(@PathVariable Long id){
        return this.reviewPodcastService.getAllPodcastReviews(id);
    }

    @GetMapping("/books/{id}")
    public List<ReviewResponse> getBookReviews(@PathVariable Long id){
        return this.reviewBookService.getAllReviewsByBook(id);
    }

    @GetMapping("/courses/{id}")
    public List<ReviewResponse> getCourseReviews(@PathVariable Long id){
        return this.reviewCourseService.getAllVideoCourseReviews(id);
    }

    @GetMapping("/videos-average-rating/{id}")
    public Double getAverageVideoReviewsRating(@PathVariable Long id){
        return this.reviewVideoService.averageVideoReviewsStars(id);
    }

    @GetMapping("/podcasts-average-rating/{id}")
    public Double getAveragePodcastsReviewsRating(@PathVariable Long id){
        return this.reviewPodcastService.averagePodcastReviewsStars(id);
    }

    @GetMapping("/books-average-rating/{id}")
    public Double getAverageBooksReviewsRating(@PathVariable Long id){
        return this.reviewBookService.averageBookReviewsStars(id);
    }

    @GetMapping("/courses-average-rating/{id}")
    public Double getAverageCoursesReviewsRating(@PathVariable Long id){
        return this.reviewCourseService.averageVideoCourseReviewsStars(id);
    }

    @PostMapping("/add-video-review")
    public ReviewVideo addVideoReview(@RequestBody AddReviewRequest addReviewRequest){
        return this.reviewVideoService.addVideoReview(addReviewRequest);
    }

    @PostMapping("/add-podcast-review")
    public ReviewPodcast addPodcastReview(@RequestBody AddReviewRequest addReviewRequest){
        return this.reviewPodcastService.addPodcastReview(addReviewRequest);
    }

    @PostMapping("/add-book-review")
    public ReviewBook addBookReview(@RequestBody AddReviewRequest addReviewRequest){
        return this.reviewBookService.addBookReview(addReviewRequest);
    }

    @PostMapping("/add-course-review")
    public ReviewCourse addCourseReview(@RequestBody AddReviewRequest addReviewRequest){
        return this.reviewCourseService.addVideoCourseReview(addReviewRequest);
    }

    @PostMapping("/edit-video-review")
    public ReviewVideo editVideoReview(@RequestBody EditReviewRequest editReviewRequest){
        return this.reviewVideoService.editVideoReview(editReviewRequest);
    }

    @PostMapping("/edit-podcast-review")
    public ReviewPodcast editPodcastReview(@RequestBody EditReviewRequest editReviewRequest){
        return this.reviewPodcastService.editPodcastReview(editReviewRequest);
    }

    @PostMapping("/edit-book-review")
    public ReviewBook editBookReview(@RequestBody EditReviewRequest editReviewRequest){
        return this.reviewBookService.editBookReview(editReviewRequest);
    }

    @PostMapping("/edit-course-review")
    public ReviewCourse editCourseReview(@RequestBody EditReviewRequest editReviewRequest){
        return this.reviewCourseService.editVideoCourseReview(editReviewRequest);
    }

    @DeleteMapping("/delete-video-review/{id}")
    public void deleteVideoReview(@PathVariable Long id){
        this.reviewVideoService.deleteReview(id);
    }

    @DeleteMapping("/delete-podcast-review/{id}")
    public void deletePodcastReview(@PathVariable Long id){
        this.reviewPodcastService.deleteReview(id);
    }

    @DeleteMapping("/delete-book-review/{id}")
    public void deleteBookReview(@PathVariable Long id){
        this.reviewBookService.deleteBookReview(id);
    }

    @DeleteMapping("/delete-course-review/{id}")
    public void deleteCourseReview(@PathVariable Long id){
        this.reviewCourseService.deleteReview(id);
    }

}
