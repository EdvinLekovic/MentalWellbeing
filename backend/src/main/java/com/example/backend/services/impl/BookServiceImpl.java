package com.example.backend.services.impl;

import com.example.backend.models.Book;
import com.example.backend.models.Image;
import com.example.backend.models.User;
import com.example.backend.models.requests.book_requests.AddBookRequest;
import com.example.backend.models.requests.book_requests.EditBookRequest;
import com.example.backend.models.requests.user_requests.ProductUserRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;
import com.example.backend.models.response.MultimediaMetadataResponse;
import com.example.backend.repositories.BookRepository;
import com.example.backend.repositories.ImageRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.BookService;
import com.example.backend.services.ReviewBookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ReviewBookService reviewBookService;

    public BookServiceImpl(BookRepository bookRepository,
                           UserRepository userRepository,
                           ImageRepository imageRepository, ReviewBookService reviewBookService) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.reviewBookService = reviewBookService;
    }

    @Override
    public List<MultimediaMetadataResponse> allBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book -> new MultimediaMetadataResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getDescription(),
                        book.getPrice(),
                        book.getUsers().size(),
                        book.getImage(),
                        book.getCreator().getFullName(),
                        reviewBookService.averageBookReviewsStars(book.getId()))).toList();
    }

    @Override
    public List<Book> allBooksByUser(UsernameRequest usernameRequest) {
        User user = userRepository.findById(usernameRequest.getUsername()).orElseThrow();
        return bookRepository.findBooksByUser(user);
    }

    @Override
    public boolean checkIfUserOwnTheBook(ProductUserRequest productUserRequest) {
        Book book = bookRepository.findById(productUserRequest.getProductId()).orElseThrow();
        return book
                .getUsers()
                .stream()
                .anyMatch(user -> productUserRequest.getUsername().equals(user.getUsername())) ||
                book.getCreator().getUsername().equals(productUserRequest.getUsername());
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    public MultimediaMetadataResponse getBookMetadata(Long id) {
        return bookRepository.findById(id).map(book -> new MultimediaMetadataResponse(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getPrice(),
                book.getUsers().size(),
                book.getImage(),
                book.getCreator().getFullName(),
                reviewBookService.averageBookReviewsStars(book.getId()))).orElseThrow();
    }

    @Override
    public Book addBook(AddBookRequest addBookRequest) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            UsernameRequest usernameRequest = mapper.readValue(addBookRequest.getUsernameRequest(), UsernameRequest.class);
            User creator = userRepository
                    .findById(usernameRequest.getUsername()).orElseThrow();
            Image image = new Image(addBookRequest.getImage().getName(), addBookRequest.getImage().getContentType(), addBookRequest.getImage().getBytes());
            imageRepository.save(image);
            Double price = mapper.readValue(addBookRequest.getPrice(), Double.class);
            return bookRepository.save(new Book(addBookRequest.getBook().getBytes(),
                    addBookRequest.getAuthor(),
                    addBookRequest.getTitle(),
                    addBookRequest.getDescription(),
                    price,
                    image,
                    creator));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book editBook(EditBookRequest editBookRequest) {
        Book book = bookRepository.findById(editBookRequest.getId()).orElseThrow();
        book.setDescription(editBookRequest.getDescription());
        book.setTitle(editBookRequest.getTitle());

        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);

    }

    @Override
    public byte[] getBookImage(Long id) {
        return bookRepository.findById(id).orElseThrow().getImage().getData();
    }

    @Override
    public byte[] getBookFile(Long id) {
        return bookRepository.findById(id).orElseThrow().getBookData();
    }
}
