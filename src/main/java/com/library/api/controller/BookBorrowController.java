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


    @PostMapping
    public ResponseEntity<Response> create(@Validated @RequestBody BookBorrowRequestDto dto) throws ResourceNotFoundException,
            ResourceExistException, BookNotAvailableException {

        Response response = borrowService.borrowedABook(dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @GetMapping("/{borrowId}")
    public ResponseEntity<Response> findById(@Validated @PathVariable(required = true) long borrowId) throws ResourceNotFoundException {
        Response response = borrowService.findById(borrowId);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateBorrowBook(@Validated @PathVariable(required = true) long id,
                                                        @RequestBody BookBorrowRequestDto dto) throws ResourceNotFoundException, BorrowBookNoUpdateException {
        Response response = borrowService.update(id, dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @GetMapping("/borrows")
    public ResponseEntity<Response> finAllBorrows(@RequestParam(defaultValue = "0") int pageNo,
                                                     @RequestParam(defaultValue = "20") int pageSize,
                                                     @RequestParam(defaultValue = "createdAt") String sortBy) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Response response = borrowService.findAll(pageRequest);
        return ResponseEntity.ok().body(response);
    }
}
