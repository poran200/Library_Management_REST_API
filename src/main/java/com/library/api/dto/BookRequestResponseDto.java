package com.library.api.dto;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookRequestResponseDto {
    private Long id;
    private String bookTitle;
    private String edition;
    private String authorName;
    private String status;
    @JsonProperty("studentId")
    private String username;
}
