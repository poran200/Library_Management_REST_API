package com.library.api.controller;/*
 * @created 7/30/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.advice.GlobalControllerAdvice;
import com.library.api.config.IsLibrarian;
import com.library.api.config.IsStudentRole;
import com.library.api.dto.BookDto;
import com.library.api.dto.Response;
import com.library.api.exception.ResourceExistException;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/book")
@RequiredArgsConstructor
public class BookController extends GlobalControllerAdvice {
    private final BookService bookService;
    @PostMapping
    @IsLibrarian
    public ResponseEntity<Response> createABook(@Validated @RequestBody BookDto dto) throws ResourceExistException {
        Response response = bookService.create(dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
    @GetMapping("/{id}")
    @IsStudentRole
    public ResponseEntity<Response> findById(@Validated @PathVariable(required = true) long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(bookService.findById(id));
    }
    @PutMapping("/{id}")
    @IsLibrarian
    public ResponseEntity<Response> updateBook(@Validated @PathVariable(required = true) long id,
                                                  @RequestBody BookDto dto ) throws ResourceNotFoundException, ResourceExistException {
        return ResponseEntity.ok().body(bookService.update(id,dto));
    }
    @GetMapping("/books")
    @IsStudentRole
    public ResponseEntity<Response> finAllBooks(@RequestParam(defaultValue = "0") int pageNo,
                                                   @RequestParam(defaultValue = "20") int pageSize,
                                                   @RequestParam(defaultValue = "title") String sortBy){
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Response response = bookService.findAllBooks(pageRequest);
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("/{id}")
    @IsLibrarian
    public ResponseEntity<Response> deleteBook(@Validated @PathVariable(required = true) long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(bookService.deleteByBookId(id));
    }
}
