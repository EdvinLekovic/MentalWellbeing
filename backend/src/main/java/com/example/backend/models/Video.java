package com.example.backend.models;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10000000)
    private byte[] videoData;
    private String title;
    @Column(length = 1000)
    private String description;
    private LocalDateTime createdOn;
    private Double price;
    @ManyToOne
    private Image image;
    @ManyToOne
    private User creator;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> users;
    @ManyToOne
    private VideoCourse videoCourse;

    public Video() {
    }

    public Video(byte[] videoData, String title, String description, Double price, Image image, User creator) {
        this.videoData = videoData;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.creator = creator;
        this.users = new ArrayList<>();
        this.createdOn = LocalDateTime.now();
    }

    public Video(byte[] videoData,
                 String title,
                 String description,
                 VideoCourse videoCourse) {
        this.videoData = videoData;
        this.title = title;
        this.description = description;
        this.videoCourse = videoCourse;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
