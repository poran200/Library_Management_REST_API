package com.library.api.advice;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.util.ResponseBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Log4j2
public class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> processValidationError(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        return ResponseEntity.badRequest()
                .body(ResponseBuilder.getFailureResponse(bindingResult,"Bean validation error path: "+request.getContextPath()));
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> requestPermRequire(MissingServletRequestParameterException ex, WebRequest request) {
        String message = ex.getMessage();
        return ResponseEntity.badRequest()
                .body(ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,message));
    }
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> handleRequest(HttpClientErrorException.Unauthorized ex, WebRequest request) {
        String message = ex.getMessage();
        return ResponseEntity.badRequest()
                .body(ResponseBuilder.getFailureResponse(HttpStatus.UNAUTHORIZED,message));
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> nohandlaerfound(NoHandlerFoundException ex, WebRequest request) {
        return ResponseEntity.badRequest()
                .body(ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,ex.getMessage()));
    }

}
