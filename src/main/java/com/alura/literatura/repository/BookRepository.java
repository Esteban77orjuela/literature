package com.alura.literatura.repository;

import com.alura.literatura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByLanguagesContainingIgnoreCase(String language);
}
