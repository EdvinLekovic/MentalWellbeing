package com.example.backend.repositories;

import com.example.backend.models.Book;
import com.example.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    @Query(value = "SELECT * FROM BOOK book JOIN BOOK_USERS bookUsers ON book.id == bookUsers.book_id",
            nativeQuery = true)
    List<Book> findBooksByUser(User user);
}
