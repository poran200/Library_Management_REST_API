package com.library.api.dto;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class LibrarianDto {
    @NotNull(message = "user id can no null")
    private Long id;
    @NotEmpty(message = "User name can not empty ")
    private String username;
    private String fistName;
    private String lastName;
    private String email;
    private String mobileNO;
    private boolean isActive;
    private boolean isAccountNotLocked;
}
