package com.hms.user_service.dto;

import com.hms.user_service.model.Role;
import lombok.Data;

@Data
public class AuthUserDto {
    private String id;
    private String username;
    private String password;
    private Role role;
}