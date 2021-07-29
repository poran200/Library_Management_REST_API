package com.library.api.dto;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentUserDto {
    private Long id;
//    @NotEmpty(message = "User Id Cannot be empty")
//    private String userId;
    @NotEmpty(message = "User name Cannot be empty")
    private String username;
    @NotEmpty(message = "Fist name Cannot be empty")
    private String fistName;
    @NotEmpty(message = "Last name Cannot be empty")
    private String lastName;
    @NotEmpty(message = "Last name Cannot be empty")
    private String email;
    private String mobileNO;
    @NotEmpty(message = "User Role Cannot be empty")
    private String role;
    private String department;
    private String batch;
    private boolean isActive;
    private boolean isAccountNotLocked;
}
