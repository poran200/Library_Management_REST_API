package com.library.api.controller;/*
 * @created 7/30/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.advice.GlobalControllerAdvice;
import com.library.api.config.IsLibrarian;
import com.library.api.dto.LibrarianDto;
import com.library.api.dto.Response;
import com.library.api.dto.StudentUserDto;
import com.library.api.exception.ResourceExistException;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * User information Rest end point's  and end points by default secure
 * need to log in as Librarian
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
@IsLibrarian
public class UserController extends GlobalControllerAdvice {
    private final UserService userService;

    /**
     * This method help to find a user need to log in as Librarian
     *
     * @param username user name must be provide | student id | librarian user name
     * @return user information
     * @throws ResourceNotFoundException if user is not found
     */
    @GetMapping("/{username}")
    public ResponseEntity<Response> finByUserName(@Validated @PathVariable(required = true) String username) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(userService.findByUserName(username));
    }

    /**
     * Find all user whose role as a student. Need to log in as Librarian
     *
     * @param pageNo   page number
     * @param pageSize page size
     * @param sortBy   sort by User any field |  by default it is username
     * @return user page
     */
    @GetMapping("/users/students")
    public ResponseEntity<Response> finAllUsersStudent(@RequestParam(defaultValue = "0") int pageNo,
                                                       @RequestParam(defaultValue = "20") int pageSize,
                                                       @RequestParam(defaultValue = "username") String sortBy) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Response response = userService.findAllStudents(pageRequest);
        return ResponseEntity.ok().body(response);
    }

    /**
     * Find all user whose role as a librarian. Need to log in as Librarian
     *
     * @param pageNo   page number
     * @param pageSize page size
     * @param sortBy   sort by User any field |  by default it is username
     * @return user page
     */
    @GetMapping("/users/librarian")
    public ResponseEntity<Response> finAllUsersLibrarian(@RequestParam(defaultValue = "0") int pageNo,
                                                         @RequestParam(defaultValue = "20") int pageSize,
                                                         @RequestParam(defaultValue = "username") String sortBy) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Response response = userService.findAllLibrarian(pageRequest);
        return ResponseEntity.ok().body(response);
    }

    /**
     * Update user whose role is student | need to log in as Librarian
     *
     * @param dto user dto with user name and id
     * @return update user response
     * @throws ResourceExistException    if username or email is exits
     * @throws ResourceNotFoundException if user is not found
     */
    @PutMapping("/student")
    public ResponseEntity<Response> updateUserStudent(@Validated @RequestBody StudentUserDto dto) throws ResourceExistException, ResourceNotFoundException {
        Response response = userService.update(dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    /**
     * Update user whose role is librarian | need to log in as Librarian
     *
     * @param dto user dto with user name and id
     * @return update user response
     * @throws ResourceExistException    if username or email is exits
     * @throws ResourceNotFoundException if user is not found
     */
    @PutMapping("/librarian")
    public ResponseEntity<Response> updateUserLibrarian(@Valid @RequestBody LibrarianDto dto) throws ResourceExistException, ResourceNotFoundException {
        Response response = userService.update(dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    /**
     * Delete an user . Need to log in as Librarian
     *
     * @param id user id not username
     * @return 200 response
     * @throws ResourceNotFoundException if user is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteById(@Validated @PathVariable(required = true) long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }

}
