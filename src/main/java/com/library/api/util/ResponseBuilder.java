package com.library.api.util;


import com.library.api.dto.ErrorResponseDto;
import com.library.api.dto.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class ResponseBuilder {
    private ResponseBuilder() { }

    private  static List<ErrorResponseDto> getCustomError(BindingResult result){
        List<ErrorResponseDto> errorResponseDtos = new ArrayList<>();
        result.getFieldErrors().forEach(fieldError->{
            ErrorResponseDto  dto = ErrorResponseDto.builder()
                      .field(fieldError.getField())
                      .massage(fieldError.getDefaultMessage())
                      .build();
            errorResponseDtos.add(dto);
        });
        return errorResponseDtos;
    }

    public static Response getFailureResponse(BindingResult bindingResult, String message){
        return Response.builder()
                .massage(message)
                .errors(getCustomError(bindingResult))
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date().getTime())
                .build();
    }
    public static Response getFailureResponse(HttpStatus httpStatus, String message){
        return Response.builder()
                .massage(message)
                .status(httpStatus.getReasonPhrase())
                .statusCode(httpStatus.value())
                .timestamp(new Date().getTime())
                .build();
    }
    public static Response getSuccessResponse(HttpStatus status, String message, Object data){
        return Response.builder()
                .massage(message)
                .status(status.getReasonPhrase())
                .statusCode( status.value())
                .data(data)
                .timestamp(new Date().getTime())
                .build();
    }
    public static <T> Response getSuccessResponseWithType(HttpStatus status, String message, T t){
        return Response.builder()
                .massage(message)
                .status(status.getReasonPhrase())
                .statusCode( status.value())
                .data(t)
                .timestamp(new Date().getTime())
                .build();
    }
    public static Response getSuccessResponsePage(HttpStatus status, String message, Page<?> page){
        return Response.builder()
                .massage(message)
                .status(status.getReasonPhrase())
                .statusCode( status.value())
                .page(page)
                .timestamp(new Date().getTime())
                .build();
    }
    public static Response getSuccessResponsePageWithReviewCount(HttpStatus status, String message, Page<?> page){
        return Response.builder()
                .massage(message)
                .status(status.getReasonPhrase())
                .statusCode( status.value())
                .page(page)
                .timestamp(new Date().getTime())
                .build();
    }
    public static Response getSuccessResponseList(HttpStatus status, String message, List<?> contentList, int numberOfElement){
        return Response.builder()
                .massage(message)
                .status(status.getReasonPhrase())
                .statusCode( status.value())
                .contentList((List<Object>) contentList)
                .timestamp(new Date().getTime())
                .numberOfElement(numberOfElement)
                .build();
    }
    public static Response getSuccessResponse(HttpStatus status, String message, Object content, int numberOfElement){
        return Response.builder()
                .massage(message)
                .status(status.getReasonPhrase())
                .statusCode( status.value())
                .data(content)
                .numberOfElement(numberOfElement)
                .timestamp(new Date().getTime())
                .build();
    }
    public static Response getSuccessResponse(HttpStatus status, String message, Object content, int numberOfElement, int rowCount){
        return Response.builder()
                .massage(message)
                .status(status.getReasonPhrase())
                .statusCode( status.value())
                .data(content)
                .numberOfElement(numberOfElement)
                .rowCount(rowCount)
                .timestamp(new Date().getTime())
                .build();
    }
    public static Response getBeanValidationError(){
        return Response.builder()
                .build();
    }
    public static Response getInternalServerError(){
        return Response.builder()
                .massage("Internal server error!")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }


}
