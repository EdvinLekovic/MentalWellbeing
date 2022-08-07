package com.example.backend.repositories;

import com.example.backend.models.ReviewCourse;
import com.example.backend.models.VideoCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewCourseRepository extends JpaRepository<ReviewCourse,Long> {
    List<ReviewCourse> findAllByVideoCourse(VideoCourse videoCourse);
}
