package com.library.api.services;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.dto.BookRequestCreateDto;
import com.library.api.dto.BookRequestResponseDto;
import com.library.api.dto.Response;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.model.BookRequest;
import com.library.api.model.BookRequestStatus;
import com.library.api.model.User;
import com.library.api.repository.BookRequestRepository;
import com.library.api.util.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookRequestService {
    private final BookRequestRepository bookRequestRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public Response create(String currentUsername, BookRequestCreateDto dto) throws ResourceNotFoundException {
        User userByName = userService.getUserByName(currentUsername);
        BookRequest bookRequest = modelMapper.map(dto, BookRequest.class);
        bookRequest.setUser(userByName);
        bookRequest.setStatus(BookRequestStatus.PENDING.name());
        BookRequest saveRequest = bookRequestRepository.save(bookRequest);
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, "Book request created",
                modelMapper.map(saveRequest, BookRequestResponseDto.class));
    }

    public Response findByStudentId(String studentId) throws ResourceNotFoundException {
        Optional<BookRequest> optionalBookRequest = bookRequestRepository.findByUser_Username(studentId);
        if (optionalBookRequest.isPresent()) {
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Book request found by user id : " + studentId,
                    modelMapper.map(optionalBookRequest.get(), BookRequestResponseDto.class));
        }
        throw new ResourceNotFoundException("Book request not found by student id: " + studentId);
    }

    public Response update(BookRequestResponseDto dto) throws ResourceNotFoundException {
        Optional<BookRequest> optionalBookRequest = bookRequestRepository.findById(dto.getId());
        if (optionalBookRequest.isPresent()) {
            BookRequest bookRequest = optionalBookRequest.get();
            if (StringUtils.isNotBlank(dto.getStatus())) {
                bookRequest.setStatus(getStatus(dto.getStatus()));
            }
            BookRequest saveBookRequest = bookRequestRepository.save(bookRequest);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Book Request Updated",
                    modelMapper.map(saveBookRequest, BookRequestResponseDto.class));
        }
        throw new ResourceNotFoundException("Book request no found id : " + dto.getId());
    }
    public Response finAllRequest(Pageable pageable){
        Page<BookRequestResponseDto> allRequest = bookRequestRepository.findAll(pageable)
                .map(bookRequest -> modelMapper.map(bookRequest,BookRequestResponseDto.class));
        return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK,"All book requests ",allRequest);
    }

    private String getStatus(String status) {
        return BookRequestStatus.valueOf(status).name();
    }
}
