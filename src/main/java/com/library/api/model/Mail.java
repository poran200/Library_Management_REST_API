package com.library.api.model;/*
 * @created 7/23/2021
 *
 * @Author Poran chowdury
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Mail {
    private String from;
    private String to;
    private String subject;
    private String content;
}
