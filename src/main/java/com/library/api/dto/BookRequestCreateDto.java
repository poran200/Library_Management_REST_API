package com.library.api.dto;/*
 * @created 7/29/2021
 *
 * @Author Poran chowdury
 */

import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
public class BookRequestCreateDto {
    private String bookTitle;
    private String edition;
    private String authorName;
}
