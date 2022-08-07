package com.example.backend.models.requests.book_requests;

import com.example.backend.models.requests.user_requests.UsernameRequest;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddBookRequest {
    private MultipartFile book;
    private String author;
    private String title;
    private String description;
    private String price;
    private String usernameRequest;
    private MultipartFile image;

    public AddBookRequest(MultipartFile book,
                          String author,
                          String title,
                          String description,
                          String price,
                          String usernameRequest,
                          MultipartFile image) {
        this.book = book;
        this.author = author;
        this.title = title;
        this.description = description;
        this.price = price;
        this.usernameRequest = usernameRequest;
        this.image = image;
    }

    public AddBookRequest() {
    }
}
