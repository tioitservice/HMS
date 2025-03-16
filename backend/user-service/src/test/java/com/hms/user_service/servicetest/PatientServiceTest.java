package com.hms.user_service.servicetest;

import com.hms.user_service.model.Patient;
import com.hms.user_service.repo.PatientRepository;
import com.hms.user_service.service.PatientService;
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
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
        testPatient = new Patient();
        testPatient.setId(1);
        testPatient.setPhysicianId(100);
        testPatient.setName("John Doe"); // Assuming there's a name field
    }

    @Test
    void createPatient_ShouldSavePatientSuccessfully() {
        // Arrange
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);

        // Act
        Patient result = patientService.createPatient(testPatient);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(100, result.getPhysicianId());
        assertEquals("John Doe", result.getName());
        verify(patientRepository, times(1)).save(testPatient);
    }

    @Test
    void getAllPatients_ShouldReturnAllPatients() {
        // Arrange
        List<Patient> patients = Arrays.asList(testPatient);
        when(patientRepository.findAll()).thenReturn(patients);

        // Act
        List<Patient> result = patientService.getAllPatients();

        // Assert
        assertEquals(1, result.size());
        assertEquals(testPatient, result.get(0));
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void getPatientsByPhysicianId_ShouldReturnPatientsForPhysician() {
        // Arrange
        List<Patient> patients = Arrays.asList(testPatient);
        when(patientRepository.findByPhysicianId(100)).thenReturn(patients);

        // Act
        List<Patient> result = patientService.getPatientsByPhysicianId(100);

        // Assert
        assertEquals(1, result.size());
        assertEquals(testPatient, result.get(0));
        verify(patientRepository, times(1)).findByPhysicianId(100);
    }

    @Test
    void getPatientsByPhysicianId_ShouldReturnEmptyList_WhenNoPatients() {
        // Arrange
        when(patientRepository.findByPhysicianId(100)).thenReturn(Arrays.asList());

        // Act
        List<Patient> result = patientService.getPatientsByPhysicianId(100);

        // Assert
        assertTrue(result.isEmpty());
        verify(patientRepository, times(1)).findByPhysicianId(100);
    }

    @Test
    void getPatientByPhysicianAndPatientId_ShouldReturnPatient_WhenExists() {
        // Arrange
        when(patientRepository.findByPhysicianIdAndId(100, 1)).thenReturn(Optional.of(testPatient));

        // Act
        Optional<Patient> result = patientService.getPatientByPhysicianAndPatientId(100, 1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testPatient, result.get());
        verify(patientRepository, times(1)).findByPhysicianIdAndId(100, 1);
    }

    @Test
    void getPatientByPhysicianAndPatientId_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        when(patientRepository.findByPhysicianIdAndId(100, 1)).thenReturn(Optional.empty());

        // Act
        Optional<Patient> result = patientService.getPatientByPhysicianAndPatientId(100, 1);

        // Assert
        assertFalse(result.isPresent());
        verify(patientRepository, times(1)).findByPhysicianIdAndId(100, 1);
    }
}