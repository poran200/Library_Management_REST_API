package com.library.api.dto;/*
 * @created 6/26/2021
 *
 * @Author Poran chowdury
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String field;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String massage;
}

