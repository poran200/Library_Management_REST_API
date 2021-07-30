package com.library.api.advice;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.exception.BookNotAvailableException;
import com.library.api.exception.BorrowBookNoUpdateException;
import com.library.api.exception.ResourceExistException;
import com.library.api.exception.ResourceNotFoundException;
import com.library.api.util.ResponseBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.Objects;

@RestControllerAdvice
@Log4j2
public class GlobalControllerAdvice{
    private static final String ACCOUNT_LOCKED = "Your account has been locked. please contract administration";
    private static final String METHOD_IS_NOT_ALLOWED ="This request is not allowed on this endpoint. Please send a '%s' request ";
    private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
    private static final String INCORRECT_CREDENTIALS= "Username / Password incorrect. Please try again";
    private static final String ACCOUNT_DISABLED ="Your account has been disabled. if this is an error , please contact administration";
    private static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
    private static final String NOT_ENOUGH_PERMISSION= "You do not have to enough permission";

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
    public ResponseEntity<Object> recourseExist(NoHandlerFoundException ex, WebRequest request) {
        return ResponseEntity.badRequest()
                .body(ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,ex.getMessage()));
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> resourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,ex.getMessage()));
    }
    @ExceptionHandler(ResourceExistException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ResponseEntity<Object> recourseExist(ResourceExistException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(ResponseBuilder.getFailureResponse(HttpStatus.NOT_ACCEPTABLE,ex.getMessage()));
    }
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Object> accountDisabledException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST,ACCOUNT_DISABLED);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredentialException(){
        return createHttpResponse(HttpStatus.BAD_REQUEST,INCORRECT_CREDENTIALS);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> accessDeniedException(){
        return createHttpResponse(HttpStatus.FORBIDDEN,NOT_ENOUGH_PERMISSION);
    }
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<Object> accountLockedException(){
        return createHttpResponse(HttpStatus.UNAUTHORIZED,ACCOUNT_LOCKED);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> methodNotSupportedException(HttpRequestMethodNotSupportedException ex){
        HttpMethod httpMethod = Objects.requireNonNull(ex.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED,httpMethod));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> internalServerErrorException(Exception ex){
        log.error(ex.getMessage());
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
    }
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Object> notFoundException(NoResultException ex){
        log.error(ex.getMessage());
        return createHttpResponse(HttpStatus.NOT_FOUND,getMessage(ex));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> massgeReadAbleException(HttpMessageNotReadableException ex){
        log.error(ex.getMessage());
        return createHttpResponse(HttpStatus.BAD_REQUEST,getMessage(ex));
    }
    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<Object> bookNotAvailableException(BookNotAvailableException ex){
        log.error(ex.getMessage());
        return createHttpResponse(HttpStatus.NOT_ACCEPTABLE,getMessage(ex));
    }
    @ExceptionHandler(BorrowBookNoUpdateException.class)
    public ResponseEntity<Object> borrowBookUpdateException(BorrowBookNoUpdateException ex){
        log.error(ex.getMessage());
        return createHttpResponse(HttpStatus.NOT_ACCEPTABLE,getMessage(ex));
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> ioException(IOException ex){
        log.error(ex.getMessage());
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR,ERROR_PROCESSING_FILE);
    }
    private String getMessage(Exception ex) {
        return ex.getMessage();
    }

    private ResponseEntity<Object> createHttpResponse(HttpStatus httpStatus, String message){

        return ResponseEntity.status(httpStatus.value()).body( ResponseBuilder.getFailureResponse(httpStatus,message));
    }

}
