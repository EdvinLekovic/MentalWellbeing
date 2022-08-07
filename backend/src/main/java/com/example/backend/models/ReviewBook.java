package com.example.backend.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ReviewBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String description;
    private Long rating;
    @ManyToOne
    private Book book;
    @ManyToOne
    private User user;

    public ReviewBook(String description, Long rating, Book book, User user) {
        this.description = description;
        this.rating = rating;
        this.book = book;
        this.user = user;
    }

    public ReviewBook() {}
}
