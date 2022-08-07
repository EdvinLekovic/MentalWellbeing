package com.example.backend.repositories;

import com.example.backend.models.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PodcastRepository extends JpaRepository<Podcast,Long> {
}
