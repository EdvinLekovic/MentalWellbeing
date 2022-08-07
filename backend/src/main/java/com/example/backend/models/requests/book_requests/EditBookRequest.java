package com.example.backend.models.requests.book_requests;

import lombok.Data;

@Data
public class EditBookRequest {

    private Long id;
    private String title;
    private String description;

    public EditBookRequest(Long id,String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public EditBookRequest() {
    }
}
