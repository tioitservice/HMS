package com.hms.user_service.servicetest;


import com.hms.user_service.exec.NotFoundException;
import com.hms.user_service.model.Active;
import com.hms.user_service.model.Role;
import com.hms.user_service.model.User;
import com.hms.user_service.repo.UserRepository;
import com.hms.user_service.request.RegisterRequest;
import com.hms.user_service.request.UserUpdateRequest;
import com.hms.user_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .username("testuser")
                .password("encodedPassword")
                .email("test@example.com")
                .role(Role.USER)
                .active(Active.ACTIVE)
                .name("Test User")
                .phoneNumber("1234567890")
                .address("123 Test St")
                .build();

        registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setPassword("password123");
        registerRequest.setEmail("test@example.com");
        registerRequest.setName("Test User");
        registerRequest.setPhoneNumber("1234567890");
        registerRequest.setAddress("123 Test St");
    }

    @Test
    void saveUser_ShouldSaveNewUserSuccessfully() {
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User savedUser = userService.saveUser(registerRequest);

        assertNotNull(savedUser);
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals(Role.USER, savedUser.getRole());
        assertEquals(Active.ACTIVE, savedUser.getActive());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAll_ShouldReturnActiveUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAllByActive(Active.ACTIVE)).thenReturn(users);

        List<User> result = userService.getAll();

        assertEquals(1, result.size());
        assertEquals(testUser, result.get(0));
        verify(userRepository, times(1)).findAllByActive(Active.ACTIVE);
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));

        User result = userService.getUserById("1");

        assertEquals(testUser, result);
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void getUserById_ShouldThrowNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById("1"));
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void updateUserById_ShouldUpdateUserSuccessfully() {
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setId("1");
        updateRequest.setUsername("updateduser");
        updateRequest.setName("Updated Name");

        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User updatedUser = userService.updateUserById(updateRequest);

        assertNotNull(updatedUser);
        verify(modelMapper, times(1)).map(updateRequest, testUser);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void deleteUserById_ShouldMarkUserAsInactive() {
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.deleteUserById("1");

        assertEquals(Active.INACTIVE, testUser.getActive());
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).save(testUser);
    }
}