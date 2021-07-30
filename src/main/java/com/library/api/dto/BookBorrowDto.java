package com.library.api.dto;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookBorrowDto {
    @JsonProperty("borrowId")
    private Long id;
    private Long bookId;
    private BookDto book;
    @JsonIgnoreProperties({"password","role","borrowBooks"})
    private User student;
    private Date returnDate;
    private String status;
}
