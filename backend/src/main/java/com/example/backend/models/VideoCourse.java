package com.example.backend.models;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class VideoCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 1000)
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;
    private Double price;
    @ManyToOne
    private User creator;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> users;
    @ManyToOne
    private Image image;

    public VideoCourse(String name, String description, Double price, User creator, Image image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.creator = creator;
        this.image = image;
        this.createdOn = LocalDateTime.now();
        this.users = new ArrayList<>();
    }

    public VideoCourse() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
