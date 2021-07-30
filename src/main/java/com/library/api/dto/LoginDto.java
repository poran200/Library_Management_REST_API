package com.library.api.dto;/*
 * @created 7/31/2021
 *
 * @Author Poran chowdury
 */

import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class LoginDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
