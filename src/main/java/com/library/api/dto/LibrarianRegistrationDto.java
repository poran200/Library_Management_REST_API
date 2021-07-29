package com.library.api.dto;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class LibrarianRegistrationDto {
    @NotEmpty(message = "User name Cannot be empty")
    private String username;
    @NotEmpty(message = "Fist name Cannot be empty")
    private String fistName;
    @NotEmpty(message = "Last name Cannot be empty")
    private String lastName;
    @NotEmpty(message = "Email name Cannot be empty")
    @Email(message = "Should be provide an valid email")
    private String email;
    @NotEmpty(message = "Mobile number Cannot be empty")
    private String mobileNO;
}
