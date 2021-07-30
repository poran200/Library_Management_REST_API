package com.library.api.dto;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Schema(description = "Book request ")
public class BookRequestResponseDto {
    @NotNull(message = "request id can not be null ")
    private Long id;
    private String bookTitle;
    private String edition;
    private String authorName;
    @Schema(description = "request status make sure input will be as like example  ", example = "ACCEPT or REJECT")
    private String status;
    @JsonProperty("studentId")
    @NotNull(message = " student id can not null ")
    @NotEmpty(message = "student id can not empty")
    private String username;
}
