package com.library.api.repository;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
}
