package com.library.api.dto;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LibrarianDto {
    private Long id;
    private String username;
    private String fistName;
    private String lastName;
    private String email;
    private String mobileNO;
    private boolean isActive;
    private boolean isAccountNotLocked;
}
