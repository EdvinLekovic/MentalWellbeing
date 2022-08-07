package com.example.backend.models;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] bookData;
    private String author;
    private String title;
    @Column(length = 1000)
    private String description;
    private Double price;
    @ManyToOne
    private Image image;
    @ManyToOne
    private User creator;
    @ManyToMany
    private List<User> users;

    private LocalDateTime createdOn;

    public Book(byte[] bookData,
                String author,
                String title,
                String description,
                Double price,
                Image image,
                User creator) {
        this.bookData = bookData;
        this.author = author;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.creator = creator;
        this.users = new ArrayList<>();
        this.createdOn = LocalDateTime.now();
    }

    public Book() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
