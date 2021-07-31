package com.library.api.controller;/*
 * @created 7/30/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.config.IsLibrarian;
import com.library.api.dto.BookBorrowRequestDto;
import com.library.api.dto.Response;
import com.library.api.exception.BookNotAvailableException;
import com.library.api.exception.BorrowBookNoUpdateException;
import com.library.api.exception.ResourceExistException;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.services.BookBorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/borrow")
@RequiredArgsConstructor
@IsLibrarian
public class BookBorrowController {
    private final BookBorrowService borrowService;


    /**
     * Librarian  borrow a book for student
     *
     * @param dto borrow book request provide the id of student and book id
     * @return Borrow book response status will be auto Borrowed and default book return date 7 days
     * @throws ResourceNotFoundException if student not found
     * @throws ResourceExistException    if book not found
     * @throws BookNotAvailableException if number of book copies are not available.
     */
    @PostMapping
    public ResponseEntity<Response> create(@Validated @RequestBody BookBorrowRequestDto dto) throws ResourceNotFoundException,
            ResourceExistException, BookNotAvailableException {

        Response response = borrowService.borrowedABook(dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    /**
     * Find the Borrow information  By id
     *
     * @param borrowId provide the borrow book id
     * @return the borrow information
     * @throws ResourceNotFoundException if borrow information not found
     */
    @GetMapping("/{borrowId}")
    public ResponseEntity<Response> findById(@Validated @PathVariable(required = true) long borrowId) throws ResourceNotFoundException {
        Response response = borrowService.findById(borrowId);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    /**
     * Update the book borrow information
     *
     * @param id  borrow id must be provide and should be valid
     * @param dto borrow book information
     * @return update book borrow information
     * @throws ResourceNotFoundException   if student or borrow information not found
     * @throws BorrowBookNoUpdateException if book are already return and try to update the  book
     */
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateBorrowBook(@Validated @PathVariable(required = true) long id,
                                                     @RequestBody BookBorrowRequestDto dto) throws ResourceNotFoundException, BorrowBookNoUpdateException {
        Response response = borrowService.update(id, dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    /**
     * Find all Borrows by page
     *
     * @param pageNo   page number
     * @param pageSize page size
     * @param sortBy   sort by field
     * @return page of borrow information
     */
    @GetMapping("/borrows")
    public ResponseEntity<Response> finAllBorrows(@RequestParam(defaultValue = "0") int pageNo,
                                                  @RequestParam(defaultValue = "20") int pageSize,
                                                  @RequestParam(defaultValue = "createdAt") String sortBy) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Response response = borrowService.findAll(pageRequest);
        return ResponseEntity.ok().body(response);
    }
}
