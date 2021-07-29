package com.library.api.dto;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {
    private Long bookId;
    private String isbn;
    private String title;
    private String authorName;
    private String edition;
    private String bookSelfNo;
    private int rowNO;
    private int columnNo;
    private int noOfCopies;
    private String status;
}
