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

    /**
     * Create a book . Require As an Librarian accesses
     *
     * @param dto Book dto{@link BookDto}
     * @return created book
     * @throws ResourceExistException if book isbn number is exits
     */
    @PostMapping
    @IsLibrarian
    public ResponseEntity<Response> createABook(@Validated @RequestBody BookDto dto) throws ResourceExistException {
        Response response = bookService.create(dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    /**
     * This method return a book by book id
     *
     * @param id book id
     * @return founded book
     * @throws ResourceNotFoundException if book not found
     */
    @GetMapping("/{id}")
    @IsStudentRole
    public ResponseEntity<Response> findById(@Validated @PathVariable(required = true) long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(bookService.findById(id));
    }

    /**
     * Update the book
     *
     * @param id  provided book id
     * @param dto corresponding book dto for update
     * @return response updated book
     * @throws ResourceNotFoundException if book not found
     * @throws ResourceExistException    if book isbn number is found
     */
    @PutMapping("/{id}")
    @IsLibrarian
    public ResponseEntity<Response> updateBook(@Validated @PathVariable(required = true) long id,
                                               @RequestBody BookDto dto) throws ResourceNotFoundException, ResourceExistException {
        return ResponseEntity.ok().body(bookService.update(id, dto));
    }

    /**
     * The page request provide by default property value its changeable
     *
     * @param pageNo   page number
     * @param pageSize page size
     * @param sortBy   sort by any book property
     * @return page of Books
     */
    @GetMapping("/books")
    @IsStudentRole
    public ResponseEntity<Response> finAllBooks(@RequestParam(defaultValue = "0") int pageNo,
                                                @RequestParam(defaultValue = "20") int pageSize,
                                                @RequestParam(defaultValue = "title") String sortBy) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Response response = bookService.findAllBooks(pageRequest);
        return ResponseEntity.ok().body(response);
    }

    /**
     * This method create for delete a book by id
     *
     * @param id book id
     * @return 200 response successfully deleted
     * @throws ResourceNotFoundException if book not found
     * @Waring if you delete a book auto delete there child entity borrow book its will be fix next version
     */
    @DeleteMapping("/{id}")
    @IsLibrarian
    public ResponseEntity<Response> deleteBook(@Validated @PathVariable(required = true) long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(bookService.deleteByBookId(id));
    }
}
