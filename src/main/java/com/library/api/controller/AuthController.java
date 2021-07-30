package com.library.api.controller;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.advice.GlobalControllerAdvice;
import com.library.api.dto.LibrarianRegistrationDto;
import com.library.api.dto.LoginDto;
import com.library.api.dto.Response;
import com.library.api.dto.StudentRegistrationDto;
import com.library.api.exception.ResourceExistException;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "User Registration and Log in  Rest API ",description = "for send request need to be login as admin or librarian on" +
        " registration user password send to there email address ")
public class AuthController extends GlobalControllerAdvice {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

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
    @PostMapping("/login")
    public ResponseEntity<Response> login(@Validated @RequestBody LoginDto dto , HttpServletRequest request) throws ResourceNotFoundException {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return ResponseEntity.ok().body(userService.findByUserName(authenticate.getName()));
    }
}
