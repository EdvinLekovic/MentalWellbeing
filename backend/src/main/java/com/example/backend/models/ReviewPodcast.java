package com.example.backend.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ReviewPodcast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String description;
    private Long rating;
    @ManyToOne
    private Podcast podcast;
    @ManyToOne
    private User user;

    public ReviewPodcast(String description, Long rating, Podcast podcast, User user) {
        this.description = description;
        this.rating = rating;
        this.podcast = podcast;
        this.user = user;
    }

    public ReviewPodcast() {}
}
