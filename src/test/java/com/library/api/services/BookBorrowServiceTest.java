package com.library.api.services;

import com.library.api.dto.BookBorrowRequestDto;
import com.library.api.dto.Response;
import com.library.api.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */
@SpringBootTest
@Rollback(value = false)
class BookBorrowServiceTest {
   @Autowired
   private BookBorrowService bookBorrowService;
    @Transactional
    @Test
    void borrowedABook() throws ResourceNotFoundException {
        Response response = bookBorrowService.borrowedABook(new BookBorrowRequestDto("2017100000028", 3L, null,null));
        assertEquals(response.getStatusCode(),HttpStatus.CREATED.value());
    }

    @Test
    void deletedById() throws ResourceNotFoundException {
        bookBorrowService.deletedborrowBook(1);
    }
}