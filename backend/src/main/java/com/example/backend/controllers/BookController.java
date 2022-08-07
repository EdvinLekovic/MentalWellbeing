package com.example.backend.controllers;

import com.example.backend.models.Book;
import com.example.backend.models.requests.book_requests.AddBookRequest;
import com.example.backend.models.requests.book_requests.EditBookRequest;
import com.example.backend.models.requests.user_requests.ProductUserRequest;
import com.example.backend.models.requests.user_requests.UsernameRequest;
import com.example.backend.models.response.MultimediaMetadataResponse;
import com.example.backend.services.BookService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin("http://localhost:4200")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<MultimediaMetadataResponse> getAllBooks(){
        return bookService.allBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    @GetMapping("/metadata/{id}")
    public MultimediaMetadataResponse getBookMetadata(@PathVariable Long id){
        return bookService.getBookMetadata(id);
    }

    @PostMapping("/check-book-own")
    public boolean checkIfUserOwnBook(@RequestBody ProductUserRequest productUserRequest){
        return this.bookService.checkIfUserOwnTheBook(productUserRequest);
    }

    @GetMapping("/books-by-user")
    public List<Book> getBooksByUser(@RequestBody UsernameRequest usernameRequest){
        return bookService.allBooksByUser(usernameRequest);
    }

    @PostMapping(value = "/add-new-book",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Book addNewBook(@RequestParam MultipartFile book,
                           @RequestParam String title,
                           @RequestParam String author,
                           @RequestParam String description,
                           @RequestParam String price,
                           @RequestParam String usernameRequest,
                           @RequestParam MultipartFile image){
        AddBookRequest addBookRequest = new AddBookRequest(book,author,title,description,price,usernameRequest,image);
        return bookService.addBook(addBookRequest);
    }

    @PostMapping("/edit-book")
    public Book editBook(@RequestBody EditBookRequest editBookRequest){
        return bookService.editBook(editBookRequest);
    }

    @DeleteMapping("/delete-book/{bookId}")
    public void deleteBook(@PathVariable Long bookId){
        bookService.deleteBook(bookId);
    }

    @GetMapping("/get-book-image/{id}")
    public byte[] getBookImage(@PathVariable Long id){
        return this.bookService.getBookImage(id);
    }

    @GetMapping("/get-book-file/{id}")
    public byte[] getBookFile(@PathVariable Long id){
        return this.bookService.getBookFile(id);
    }
}
