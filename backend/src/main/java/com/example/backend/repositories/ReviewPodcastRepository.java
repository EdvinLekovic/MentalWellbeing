package com.example.backend.repositories;

import com.example.backend.models.Podcast;
import com.example.backend.models.ReviewPodcast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewPodcastRepository extends JpaRepository<ReviewPodcast,Long> {
    List<ReviewPodcast> findAllByPodcast(Podcast podcast);
}
