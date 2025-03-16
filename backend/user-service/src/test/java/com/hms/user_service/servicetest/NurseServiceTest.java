package com.hms.user_service.servicetest;

import com.hms.user_service.model.Nurse;
import com.hms.user_service.repo.NurseRepository;
import com.hms.user_service.service.NurseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NurseServiceTest {

    @Mock
    private NurseRepository nurseRepository;

    @InjectMocks
    private NurseService nurseService;

    private Nurse testNurse;

    @BeforeEach
    void setUp() {
        testNurse = new Nurse();
        testNurse.setEmpId(1);
        testNurse.setPosition("RN");
        testNurse.setRegistered(true);
    }

    @Test
    void addNurse_ShouldSaveNurseSuccessfully() {
        // Arrange
        when(nurseRepository.save(any(Nurse.class))).thenReturn(testNurse);

        // Act
        Nurse result = nurseService.addNurse(testNurse);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getEmpId());
        assertEquals("RN", result.getPosition());
        assertTrue(result.isRegistered());
        verify(nurseRepository, times(1)).save(testNurse);
    }

    @Test
    void getAllNurses_ShouldReturnAllNurses() {
        // Arrange
        List<Nurse> nurses = Arrays.asList(testNurse);
        when(nurseRepository.findAll()).thenReturn(nurses);

        // Act
        List<Nurse> result = nurseService.getAllNurses();

        // Assert
        assertEquals(1, result.size());
        assertEquals(testNurse, result.get(0));
        verify(nurseRepository, times(1)).findAll();
    }

    @Test
    void getNurseByEmpId_ShouldReturnNurse_WhenExists() {
        // Arrange
        when(nurseRepository.findByEmpId(1)).thenReturn(Optional.of(testNurse));

        // Act
        Optional<Nurse> result = nurseService.getNurseByEmpId(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testNurse, result.get());
        verify(nurseRepository, times(1)).findByEmpId(1);
    }

    @Test
    void getNurseByEmpId_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        when(nurseRepository.findByEmpId(1)).thenReturn(Optional.empty());

        // Act
        Optional<Nurse> result = nurseService.getNurseByEmpId(1);

        // Assert
        assertFalse(result.isPresent());
        verify(nurseRepository, times(1)).findByEmpId(1);
    }

    @Test
    void getNursePosition_ShouldReturnPosition_WhenNurseExists() {
        // Arrange
        when(nurseRepository.findPositionByEmpId(1)).thenReturn(Optional.of("RN"));

        // Act
        Optional<String> result = nurseService.getNursePosition(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("RN", result.get());
        verify(nurseRepository, times(1)).findPositionByEmpId(1);
    }

    @Test
    void getNursePosition_ShouldReturnEmpty_WhenNurseNotExists() {
        // Arrange
        when(nurseRepository.findPositionByEmpId(1)).thenReturn(Optional.empty());

        // Act
        Optional<String> result = nurseService.getNursePosition(1);

        // Assert
        assertFalse(result.isPresent());
        verify(nurseRepository, times(1)).findPositionByEmpId(1);
    }

    @Test
    void isNurseRegistered_ShouldReturnRegisteredStatus_WhenNurseExists() {
        // Arrange
        when(nurseRepository.findIsRegisteredByEmpId(1)).thenReturn(Optional.of(true));

        // Act
        Optional<Boolean> result = nurseService.isNurseRegistered(1);

        // Assert
        assertTrue(result.isPresent());
        assertTrue(result.get());
        verify(nurseRepository, times(1)).findIsRegisteredByEmpId(1);
    }

    @Test
    void isNurseRegistered_ShouldReturnEmpty_WhenNurseNotExists() {
        // Arrange
        when(nurseRepository.findIsRegisteredByEmpId(1)).thenReturn(Optional.empty());

        // Act
        Optional<Boolean> result = nurseService.isNurseRegistered(1);

        // Assert
        assertFalse(result.isPresent());
        verify(nurseRepository, times(1)).findIsRegisteredByEmpId(1);
    }

    @Test
    void updateNurseRegistration_ShouldUpdateSuccessfully_WhenNurseExists() {
        // Arrange
        when(nurseRepository.findByEmpId(1)).thenReturn(Optional.of(testNurse));
        when(nurseRepository.save(any(Nurse.class))).thenReturn(testNurse);

        // Act
        Optional<Nurse> result = nurseService.updateNurseRegistration(1, false);

        // Assert
        assertTrue(result.isPresent());
        assertFalse(result.get().isRegistered());
        verify(nurseRepository, times(1)).findByEmpId(1);
        verify(nurseRepository, times(1)).save(testNurse);
    }

    @Test
    void updateNurseRegistration_ShouldReturnEmpty_WhenNurseNotExists() {
        // Arrange
        when(nurseRepository.findByEmpId(1)).thenReturn(Optional.empty());

        // Act
        Optional<Nurse> result = nurseService.updateNurseRegistration(1, false);

        // Assert
        assertFalse(result.isPresent());
        verify(nurseRepository, times(1)).findByEmpId(1);
        verify(nurseRepository, never()).save(any(Nurse.class));
    }
}
