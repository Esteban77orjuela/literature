package com.alura.literatura.repository;

import com.alura.literatura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author a WHERE :year BETWEEN a.birthYear AND a.deathYear OR " +
            "(a.birthYear <= :year AND a.deathYear IS NULL)")
    List<Author> findAuthorsAliveInYear(Integer year);
}
