package com.hms.auth_service.controller;

import com.hms.auth_service.dto.RegisterDto;
import com.hms.auth_service.dto.TokenDto;
import com.hms.auth_service.dto.UserDto;
import com.hms.auth_service.request.LoginRequest;
import com.hms.auth_service.request.RegisterRequest;
import com.hms.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterDto> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    @GetMapping("/test")
    public String test() {
        return "test complete";
    }
    @GetMapping("/getUserByUsername/{username}")
    public ResponseEntity<ResponseEntity<UserDto>> getUserDetail(@PathVariable String username) {
        return ResponseEntity.ok(authService.getUserDetail(username));
    }
}