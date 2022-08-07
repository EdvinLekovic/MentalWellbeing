package com.example.backend.models.response;

import com.example.backend.models.Image;
import lombok.Data;

@Data
public class MultimediaMetadataResponse {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private int numOfUsers;
    private Image image;
    private String creatorFullName;
    private Double rating;

    public MultimediaMetadataResponse(Long id,
                                      String title,
                                      String description,
                                      Double price,
                                      int numOfUsers,
                                      Image image,
                                      String creatorFullName,
                                      Double rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.numOfUsers = numOfUsers;
        this.image = image;
        this.creatorFullName = creatorFullName;
        this.rating = rating;
    }
}
