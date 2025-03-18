package com.hms.auth_service.request;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String password;
    private String username;
    private String email;
    private String name;
    private String phoneNumber;
    private String address;
}