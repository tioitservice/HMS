package com.hms.user_service.controller;

import com.hms.user_service.dto.AuthUserDto;
import com.hms.user_service.dto.UserDto;
import com.hms.user_service.request.RegisterRequest;
import com.hms.user_service.request.UserUpdateRequest;
import com.hms.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
//@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/save")
    public ResponseEntity<UserDto> save(@Valid @RequestBody RegisterRequest request) {
        System.out.println(request.getAddress() + " " + request.getName());
        return ResponseEntity.ok(modelMapper.map(userService.saveUser(request), UserDto.class));
    }

    @GetMapping("/getAll")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class)).toList());
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(userService.getUserById(id), UserDto.class));
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(modelMapper.map(userService.getUserByEmail(email), UserDto.class));
    }

    @GetMapping("/getUserByUsername/{username}")
    public ResponseEntity<AuthUserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(modelMapper.map(userService.getUserByUsername(username), AuthUserDto.class));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#request.id).username == principal")
    public ResponseEntity<UserDto> updateUserById(@Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(modelMapper.map(userService.updateUserById(request), UserDto.class));
    }

    @DeleteMapping("/deleteUserById/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#id).username == principal")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}