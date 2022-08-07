package com.example.backend.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ReviewCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String description;
    private Long rating;
    @ManyToOne
    private VideoCourse videoCourse;
    @ManyToOne
    private User user;

    public ReviewCourse(String description, Long rating, VideoCourse videoCourse, User user) {
        this.description = description;
        this.rating = rating;
        this.videoCourse = videoCourse;
        this.user = user;
    }

    public ReviewCourse() {}
}
