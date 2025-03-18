package com.hms.user_service.servicetest;


import com.hms.user_service.model.TrainedIn;
import com.hms.user_service.repo.TrainedInRepository;
import com.hms.user_service.service.TrainedInService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainedInServiceTest {

    @Mock
    private TrainedInRepository trainedInRepository;

    @InjectMocks
    private TrainedInService trainedInService;

    private TrainedIn testTrainedIn;

    @BeforeEach
    void setUp() {
        testTrainedIn = new TrainedIn();
//        testTrainedIn.setProcedureId(100);
//        testTrainedIn.setCertificationExpiryDate(LocalDate.of(2025, 4, 15));
    }

//    @Test
//    void addCertification_ShouldSaveCertificationSuccessfully() {
//        // Arrange
//        when(trainedInRepository.save(any(TrainedIn.class))).thenReturn(testTrainedIn);
//
//        // Act
//        TrainedIn result = trainedInService.addCertification(testTrainedIn);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1, result.getPhysicianId());
//        assertEquals(100, result.getProcedureId());
//        assertEquals(LocalDate.of(2025, 4, 15), result.getCertificationExpiryDate());
//        verify(trainedInRepository, times(1)).save(testTrainedIn);
//    }

    @Test
    void getAllTrainedProcedures_ShouldReturnAllCertifications() {
        // Arrange
        List<TrainedIn> trainedIns = Arrays.asList(testTrainedIn);
        when(trainedInRepository.findAll()).thenReturn(trainedIns);

        // Act
        List<TrainedIn> result = trainedInService.getAllTrainedProcedures();

        // Assert
        assertEquals(1, result.size());
        assertEquals(testTrainedIn, result.get(0));
        verify(trainedInRepository, times(1)).findAll();
    }

//    @Test
//    void getTreatmentsByPhysicianId_ShouldReturnPhysicianTreatments() {
//        // Arrange
//        List<TrainedIn> treatments = Arrays.asList(testTrainedIn);
//        when(trainedInRepository.findB(1)).thenReturn(treatments);
//
//        // Act
//        List<TrainedIn> result = trainedInService.getTreatmentsByPhysicianId(1);
//
//        // Assert
//        assertEquals(1, result.size());
//        assertEquals(testTrainedIn, result.get(0));
//        verify(trainedInRepository, times(1)).findByPhysicianId(1);
//    }

//    @Test
//    void getPhysiciansForProcedure_ShouldReturnPhysiciansForProcedure() {
//        // Arrange
//        List<TrainedIn> physicians = Arrays.asList(testTrainedIn);
//        when(trainedInRepository.findByProcedureId(100)).thenReturn(physicians);
//
//        // Act
//        List<TrainedIn> result = trainedInService.getPhysiciansForProcedure(100);
//
//        // Assert
//        assertEquals(1, result.size());
//        assertEquals(testTrainedIn, result.get(0));
//        verify(trainedInRepository, times(1)).findByProcedureId(100);
//    }

//    @Test
//    void getProceduresExpiringSoon_ShouldReturnExpiringProcedures() {
//        // Arrange
//        LocalDate currentDate = LocalDate.now();
//        LocalDate expiryDateLimit = currentDate.plusMonths(1);
//        List<TrainedIn> expiring = Arrays.asList(testTrainedIn);
//        when(trainedInRepository.findByPhysicianIdAndCertificationExpiryDateBetween(
//                1, currentDate, expiryDateLimit)).thenReturn(expiring);
//
//        // Act
//        List<TrainedIn> result = trainedInService.getProceduresExpiringSoon(1);
//
//        // Assert
//        assertEquals(1, result.size());
//        assertEquals(testTrainedIn, result.get(0));
//        verify(trainedInRepository, times(1))
//                .findByPhysicianIdAndCertificationExpiryDateBetween(1, currentDate, expiryDateLimit);
//    }

    @Test
    void updateCertificationExpiry_ShouldUpdateSuccessfully_WhenCertificationExists() {
        // Arrange
        when(trainedInRepository.findById(1)).thenReturn(Optional.of(testTrainedIn));
        when(trainedInRepository.save(any(TrainedIn.class))).thenReturn(testTrainedIn);
        LocalDate newExpiryDate = LocalDate.of(2025, 6, 30);

        // Act
        verify(trainedInRepository, times(1)).findById(1);
        verify(trainedInRepository, times(1)).save(testTrainedIn);
    }

    @Test
    void updateCertificationExpiry_ShouldReturnFalse_WhenCertificationNotExists() {
        // Arrange
        when(trainedInRepository.findById(1)).thenReturn(Optional.empty());
        LocalDate newExpiryDate = LocalDate.of(2025, 6, 30);

        // Act

        verify(trainedInRepository, times(1)).findById(1);
        verify(trainedInRepository, never()).save(any(TrainedIn.class));
    }

    @Test
    void updateCertificationExpiry_ShouldReturnFalse_WhenProcedureIdDoesNotMatch() {
        // Arrange
        when(trainedInRepository.findById(1)).thenReturn(Optional.of(testTrainedIn));
        LocalDate newExpiryDate = LocalDate.of(2025, 6, 30);


        verify(trainedInRepository, times(1)).findById(1);
        verify(trainedInRepository, never()).save(any(TrainedIn.class));
    }
}
