package com.hms.user_service.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "Id is required")
    private String id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String phoneNumber;
    private String address;
}