package com.library.api.dto;/*
 * @created 6/26/2021
 *
 * @Author Poran chowdury
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class Response implements Serializable {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private long timestamp;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private  long statusCode;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String massage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private  int numberOfElement;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private  long rowCount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorResponseDto> errors;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Object> contentList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Page<?> page;


}

