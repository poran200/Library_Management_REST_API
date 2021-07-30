package com.library.api.controller;/*
 * @created 7/30/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.dto.BookRequestCreateDto;
import com.library.api.dto.BookRequestResponseDto;
import com.library.api.dto.Response;
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

@RestController
@RequestMapping("api/v1/bookRequest")
@RequiredArgsConstructor
public class BooKRequestController {
    private final BookRequestService bookRequestService;
    @PostMapping
    public ResponseEntity<Response> create(@Validated @RequestBody BookRequestCreateDto dto) throws ResourceNotFoundException {
        String currentUsername = getCurrentUsername();
        if (currentUsername ==null){
            // for dev env
            currentUsername = "2017100000029";
        }
        Response response = bookRequestService.create(currentUsername, dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> findById(@Validated @PathVariable(required = true) long id) throws ResourceNotFoundException {
         return ResponseEntity.ok().body(bookRequestService.findById(id));
    }
    @GetMapping("/student/{studentId}")
    public ResponseEntity<Response> findById(@Validated @PathVariable(required = true) String studentId) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(bookRequestService.findByStudentId(studentId));
    }
    @GetMapping("/requests")
    public ResponseEntity<Response> findAllRequests(@RequestParam(defaultValue = "0") int pageNo,
                                                    @RequestParam(defaultValue = "20") int pageSize,
                                                    @RequestParam(defaultValue = "createdAt") String sortBy){
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok().body(bookRequestService.finAllRequest(pageRequest));
    }
    @PutMapping
   public ResponseEntity<Response> update(@Validated @RequestBody BookRequestResponseDto dto) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(bookRequestService.update(dto));
   }
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserPrincipal){
            UserPrincipal userPrincipal =(UserPrincipal) principal;
            return userPrincipal.getUsername();
        }
        return null;
    }
}
