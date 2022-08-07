package com.example.backend.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ReviewVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String description;
    private Long rating;
    @ManyToOne
    private Video video;
    @ManyToOne
    private User user;

    public ReviewVideo(String description, Long rating, Video video, User user) {
        this.description = description;
        this.rating = rating;
        this.video = video;
        this.user = user;
    }

    public ReviewVideo() {

    }
}
