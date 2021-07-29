package com.library.api.repository;

import com.library.api.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    void createBook() {
        Book book = new Book(null, "1114", "Java", "Jone deo",
                "2th", "A2", 2, 3, 4, "Available");
        bookRepository.save(book);
    }
}