package com.library.api.dto;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
@AllArgsConstructor
@Schema(description = "Book Borrow request model")
public class BookBorrowRequestDto {
    @JsonProperty("studentId")
    @NotEmpty(message = "student id can not be empty")
    String username;
    @NotNull(message = "Book id can not be empty")
    Long bookId;
    @Schema(description = "Book return date ")
    Date returnDate;
    @Schema(description = "Should be follow the example otherwise error response thrown " ,example = "returned or borrowed ")
    String status;
}
