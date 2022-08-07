package com.example.backend.repositories;

import com.example.backend.models.ReviewVideo;
import com.example.backend.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewVideoRepository extends JpaRepository<ReviewVideo,Long> {
    List<ReviewVideo> findReviewVideoByVideo(Video video);
}
