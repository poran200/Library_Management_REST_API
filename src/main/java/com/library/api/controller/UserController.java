package com.library.api.controller;/*
 * @created 7/30/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.advice.GlobalControllerAdvice;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController  extends GlobalControllerAdvice {
    private final UserService userService;
    @GetMapping("/{username}")
    public ResponseEntity<Response> finByUserName(@Validated  @PathVariable(required = true) String username) throws ResourceNotFoundException {
       return ResponseEntity.ok().body(userService.findByUserName(username));
    }
    @GetMapping("/users/students")
    public ResponseEntity<Response> finAllUsersStudent(@RequestParam(defaultValue = "0") int pageNo,
                                                          @RequestParam(defaultValue = "20") int pageSize,
                                                          @RequestParam(defaultValue = "username") String sortBy){
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Response response = userService.findAllStudents(pageRequest);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/users/librarian")
    public ResponseEntity<Response> finAllUsersLibrarian(@RequestParam(defaultValue = "0") int pageNo,
                                                            @RequestParam(defaultValue = "20") int pageSize,
                                                            @RequestParam(defaultValue = "username") String sortBy){
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Response response = userService.findAllLibrarian(pageRequest);
        return ResponseEntity.ok().body(response);
    }
    @PutMapping("/student")
    public ResponseEntity<Response> updateUserStudent(@Validated @RequestBody StudentUserDto dto) throws ResourceExistException, ResourceNotFoundException {
        Response response = userService.update(dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
    @PutMapping("/librarian")
    public ResponseEntity<Response> updateUserLibrarian(@Valid @RequestBody LibrarianDto dto) throws ResourceExistException, ResourceNotFoundException {
        Response response = userService.update(dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteById(@Validated @PathVariable(required = true) long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }

}
