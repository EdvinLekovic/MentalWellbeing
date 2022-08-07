package com.example.backend.repositories;

import com.example.backend.models.Book;
import com.example.backend.models.ReviewBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewBookRepository extends JpaRepository<ReviewBook, Long> {
    List<ReviewBook> findAllByBook(Book book);
}
