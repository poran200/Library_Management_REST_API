package com.library.api.dto;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
@Data
@AllArgsConstructor
public class BookBorrowRequestDto {
    @JsonProperty("studentId")
    @NotEmpty(message = "student id can not be empty")
    String username;
    @NotEmpty(message = "Book id can not be empty")
    Long bookId;
    Date returnDate;
    String status;
}
