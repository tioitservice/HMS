package com.hms.user_service.servicetest;


import com.hms.user_service.model.Physician;
import com.hms.user_service.repo.PhysicianRepository;
import com.hms.user_service.service.PhysicianService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhysicianServiceTest {

    @Mock
    private PhysicianRepository physicianRepository;

    @InjectMocks
    private PhysicianService physicianService;

    private Physician testPhysician;

    @BeforeEach
    void setUp() {
        testPhysician = new Physician();
        testPhysician.setEmployeeId(1);
        testPhysician.setName("Dr. John Doe");
        testPhysician.setPosition("Surgeon");
    }

    @Test
    void createPhysician_ShouldSavePhysicianSuccessfully() {
        // Arrange
        when(physicianRepository.save(any(Physician.class))).thenReturn(testPhysician);

        // Act
        Physician result = physicianService.createPhysician(testPhysician);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getEmployeeId());
        assertEquals("Dr. John Doe", result.getName());
        assertEquals("Surgeon", result.getPosition());
        verify(physicianRepository, times(1)).save(testPhysician);
    }

    @Test
    void getPhysicianByName_ShouldReturnPhysician_WhenExists() {
        // Arrange
        when(physicianRepository.findByName("Dr. John Doe")).thenReturn(Optional.of(testPhysician));

        // Act
        Optional<Physician> result = physicianService.getPhysicianByName("Dr. John Doe");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testPhysician, result.get());
        verify(physicianRepository, times(1)).findByName("Dr. John Doe");
    }

    @Test
    void getPhysicianByName_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        when(physicianRepository.findByName("Dr. Jane Doe")).thenReturn(Optional.empty());

        // Act
        Optional<Physician> result = physicianService.getPhysicianByName("Dr. Jane Doe");

        // Assert
        assertFalse(result.isPresent());
        verify(physicianRepository, times(1)).findByName("Dr. Jane Doe");
    }

    @Test
    void getPhysicianByPosition_ShouldReturnPhysician_WhenExists() {
        // Arrange
        when(physicianRepository.findByPosition("Surgeon")).thenReturn(Optional.of(testPhysician));

        // Act
        Optional<Physician> result = physicianService.getPhysicianByPosition("Surgeon");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testPhysician, result.get());
        verify(physicianRepository, times(1)).findByPosition("Surgeon");
    }

    @Test
    void getPhysicianByEmployeeId_ShouldReturnPhysician_WhenExists() {
        // Arrange
        when(physicianRepository.findByEmployeeId(1)).thenReturn(Optional.of(testPhysician));

        // Act
        Optional<Physician> result = physicianService.getPhysicianByEmployeeId(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testPhysician, result.get());
        verify(physicianRepository, times(1)).findByEmployeeId(1);
    }

    @Test
    void getPhysicianByEmployeeId_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        when(physicianRepository.findByEmployeeId(2)).thenReturn(Optional.empty());

        // Act
        Optional<Physician> result = physicianService.getPhysicianByEmployeeId(2);

        // Assert
        assertFalse(result.isPresent());
        verify(physicianRepository, times(1)).findByEmployeeId(2);
    }

    @Test
    void updatePosition_ShouldUpdateSuccessfully_WhenPhysicianExists() {
        // Arrange
        when(physicianRepository.findByEmployeeId(1)).thenReturn(Optional.of(testPhysician));
        when(physicianRepository.save(any(Physician.class))).thenReturn(testPhysician);

        // Act
        Physician result = physicianService.updatePosition("Cardiologist", 1);

        // Assert
        assertNotNull(result);
        assertEquals("Cardiologist", result.getPosition());
        verify(physicianRepository, times(1)).findByEmployeeId(1);
        verify(physicianRepository, times(1)).save(testPhysician);
    }

    @Test
    void updatePosition_ShouldReturnNull_WhenPhysicianNotExists() {
        // Arrange
        when(physicianRepository.findByEmployeeId(1)).thenReturn(Optional.empty());

        // Act
        Physician result = physicianService.updatePosition("Cardiologist", 1);

        // Assert
        assertNull(result);
        verify(physicianRepository, times(1)).findByEmployeeId(1);
        verify(physicianRepository, never()).save(any(Physician.class));
    }

//    @Test
//    void updateName_ShouldUpdateSuccessfully_WhenPhysicianExists() {
//        // Arrange
//        when(physicianRepository.findByEmployeeId(1)).thenReturn(Optional.of(testPhysician));
//        when(physicianRepository.save(any(Physician.class))).thenReturn(testPhysician);
//
//        // Act
//        Physician result = physicianService.updateName(1, "Dr. Jane Doe");
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("Dr. Jane Doe", result.getName());
//        verify(physicianRepository, times(1)).findByEmployeeId(1);
//        verify(physicianRepository, times(1)).save(testPhysician);
//    }

//    @Test
//    void updateName_ShouldReturnNull_WhenPhysicianNotExists() {
//        // Arrange
//        when(physicianRepository.findByEmployeeId(1)).thenReturn(Optional.empty());
//
//        // Act
//        Physician result = physicianService.updateName(1, "Dr. Jane Doe");
//
//        // Assert
//        assertNull(result);
//        verify(physicianRepository, times(1)).findByEmployeeId(1);
//        verify(physicianRepository, never()).save(any(Physician.class));
//    }
}
