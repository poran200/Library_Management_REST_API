package com.library.api.controller;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.advice.GlobalControllerAdvice;
import com.library.api.dto.LibrarianRegistrationDto;
import com.library.api.dto.Response;
import com.library.api.dto.StudentRegistrationDto;
import com.library.api.exception.ResourceExistException;
import com.library.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends GlobalControllerAdvice {
    private final UserService userService;
    @PostMapping("/registration/student")
    public ResponseEntity<Response> registerStudent(@Validated @RequestBody StudentRegistrationDto dto) throws ResourceExistException {
        Response response = userService.register(dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
    @PostMapping("/registration/liberian")
    public ResponseEntity<Response> registerLiberian(@Validated @RequestBody LibrarianRegistrationDto dto) throws ResourceExistException {
        Response response = userService.register(dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
}
