package com.example.backend.models;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Podcast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10000000)
    private byte[] podcastData;
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

    public Podcast(byte[] podcastData, String title, String description, Double price, Image image, User creator) {
        this.podcastData = podcastData;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.creator = creator;
        this.createdOn = LocalDateTime.now();
        this.users = new ArrayList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Podcast() {

    }
}
