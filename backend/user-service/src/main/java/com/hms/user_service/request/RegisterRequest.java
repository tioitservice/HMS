package com.hms.user_service.request;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class RegisterRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 6, message = "Username must be at least 6 characters")
    private String username;
    @NotNull(message = "Password is required")
    private String password;
//    @Email(message = "Email should be valid")
    private String email;
    private String name;
    private String phoneNumber;
    private String address;
}