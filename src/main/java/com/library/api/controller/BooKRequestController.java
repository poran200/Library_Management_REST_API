package com.library.api.controller;/*
 * @created 7/30/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.config.IsLibrarian;
import com.library.api.config.IsStudentRole;
import com.library.api.dto.BookRequestCreateDto;
import com.library.api.dto.BookRequestResponseDto;
import com.library.api.dto.Response;
import com.library.api.exception.BookRequestNotAcceptException;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.model.UserPrincipal;
import com.library.api.services.BookRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * All rest end point for book request
 */
@RestController
@RequestMapping("api/v1/bookRequest")
@RequiredArgsConstructor
public class BooKRequestController {
    private final BookRequestService bookRequestService;

    /**
     * Create a book request for add a book to library  by student user role
     *
     * @param dto book request create dto
     * @return the created request
     * @throws ResourceNotFoundException     if user not found
     * @throws BookRequestNotAcceptException if user already submit a request and it is pending
     */
    @PostMapping
    @IsStudentRole
    public ResponseEntity<Response> create(@Validated @RequestBody BookRequestCreateDto dto) throws ResourceNotFoundException, BookRequestNotAcceptException {
        String currentUsername = getCurrentUsername();
        if (currentUsername == null) {
            // for dev env
            currentUsername = "2017100000029";
        }
        Response response = bookRequestService.create(currentUsername, dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    /**
     * @param id book request id
     * @return  response the book request which one is found
     * @throws ResourceNotFoundException if book request no found
     */
    @IsLibrarian
    @GetMapping("/{id}")
    public ResponseEntity<Response> findById(@Validated @PathVariable(required = true) long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(bookRequestService.findById(id));
    }

    /**
     * @param studentId require the student id
     * @return the book request by student
     * @throws ResourceNotFoundException if book request not found
     */
    @IsStudentRole
    @GetMapping("/student/{studentId}")
    public ResponseEntity<Response> findById(@Validated @PathVariable(required = true) String studentId) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(bookRequestService.findByStudentId(studentId));
    }

    /**
     * Find all book requests. Requires The librarian access
     *
     * @param pageNo   which page user want
     * @param pageSize page size user can customize it
     * @param sortBy   sort by particular field which one user want
     * @return page of  book request
     */
    @GetMapping("/requests")
    @IsLibrarian
    public ResponseEntity<Response> findAllRequests(@RequestParam(defaultValue = "0") int pageNo,
                                                    @RequestParam(defaultValue = "20") int pageSize,
                                                    @RequestParam(defaultValue = "createdAt") String sortBy) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok().body(bookRequestService.finAllRequest(pageRequest));
    }

    /**
     * Update the book request . Requires The librarian access
     *
     * @param dto book request response dto must be include the request id
     * @return the update book request response
     * @throws ResourceNotFoundException if book request not found in database
     */
    @PutMapping
    @IsLibrarian
    public ResponseEntity<Response> update(@Validated @RequestBody BookRequestResponseDto dto) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(bookRequestService.update(dto));
    }

    /**
     * @return current authenticate user name form security context
     */
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) principal;
            return userPrincipal.getUsername();
        }
        return null;
    }
}
