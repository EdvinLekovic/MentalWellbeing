package com.example.backend.services;

import com.example.backend.models.Book;
import com.example.backend.models.requests.book_requests.AddBookRequest;
import com.example.backend.models.requests.book_requests.EditBookRequest;
import com.example.backend.models.requests.user_requests.ProductUserRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;
import com.example.backend.models.response.MultimediaMetadataResponse;

import java.util.List;

public interface BookService {
    List<MultimediaMetadataResponse> allBooks();
    List<Book> allBooksByUser(UsernameRequest usernameRequest);
    boolean checkIfUserOwnTheBook(ProductUserRequest productUserRequest);
    Book getBookById(Long id);
    MultimediaMetadataResponse getBookMetadata(Long id);
    Book addBook(AddBookRequest addBookRequest);
    Book editBook(EditBookRequest editBookRequest);
    void deleteBook(Long bookId);
    byte[] getBookImage(Long id);
    byte[] getBookFile(Long id);
}
