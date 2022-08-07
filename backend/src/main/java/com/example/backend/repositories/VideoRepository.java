package com.example.backend.repositories;

import com.example.backend.models.Video;
import com.example.backend.models.VideoCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video,Long> {
    List<Video> findAllByVideoCourse(VideoCourse videoCourse);
}
