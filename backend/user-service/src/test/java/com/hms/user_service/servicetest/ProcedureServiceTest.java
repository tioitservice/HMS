package com.hms.user_service.servicetest;


import com.hms.user_service.model.Procedure;
import com.hms.user_service.repo.ProcedureRepository;
import com.hms.user_service.service.ProcedureService;
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
class ProcedureServiceTest {

    @Mock
    private ProcedureRepository procedureRepository;

    @InjectMocks
    private ProcedureService procedureService;

    private Procedure testProcedure;

    @BeforeEach
    void setUp() {
        testProcedure = new Procedure();
        testProcedure.setId(1);
        testProcedure.setName("Surgery");
        testProcedure.setCost(1500.00);
    }

    @Test
    void createProcedure_ShouldSaveProcedureSuccessfully() {
        // Arrange
        when(procedureRepository.save(any(Procedure.class))).thenReturn(testProcedure);

        // Act
        Procedure result = procedureService.createProcedure(testProcedure);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Surgery", result.getName());
        assertEquals(1500.00, result.getCost(), 0.01);
        verify(procedureRepository, times(1)).save(testProcedure);
    }

    @Test
    void getAllProcedures_ShouldReturnAllProcedures() {
        // Arrange
        List<Procedure> procedures = Arrays.asList(testProcedure);
        when(procedureRepository.findAll()).thenReturn(procedures);

        // Act
        Iterable<Procedure> result = procedureService.getAllProcedures();

        // Assert
        assertNotNull(result);
        List<Procedure> resultList = Arrays.asList(result.iterator().next());
        assertEquals(1, resultList.size());
        assertEquals(testProcedure, resultList.get(0));
        verify(procedureRepository, times(1)).findAll();
    }

    @Test
    void getProcedureById_ShouldReturnProcedure_WhenExists() {
        // Arrange
        when(procedureRepository.findById(1)).thenReturn(Optional.of(testProcedure));

        // Act
        Optional<Procedure> result = procedureService.getProcedureById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testProcedure, result.get());
        verify(procedureRepository, times(1)).findById(1);
    }

    @Test
    void getProcedureById_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        when(procedureRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<Procedure> result = procedureService.getProcedureById(1);

        // Assert
        assertFalse(result.isPresent());
        verify(procedureRepository, times(1)).findById(1);
    }

    @Test
    void getProcedureByName_ShouldReturnProcedure_WhenExists() {
        // Arrange
        when(procedureRepository.findByName("Surgery")).thenReturn(Optional.of(testProcedure));

        // Act
        Optional<Procedure> result = procedureService.getProcedureByName("Surgery");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testProcedure, result.get());
        verify(procedureRepository, times(1)).findByName("Surgery");
    }

    @Test
    void getProcedureByName_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        when(procedureRepository.findByName("Consultation")).thenReturn(Optional.empty());

        // Act
        Optional<Procedure> result = procedureService.getProcedureByName("Consultation");

        // Assert
        assertFalse(result.isPresent());
        verify(procedureRepository, times(1)).findByName("Consultation");
    }

    @Test
    void updateProcedureCost_ShouldUpdateSuccessfully_WhenProcedureExists() {
        // Arrange
        when(procedureRepository.findById(1)).thenReturn(Optional.of(testProcedure));
        when(procedureRepository.save(any(Procedure.class))).thenReturn(testProcedure);

        // Act
        Optional<Procedure> result = procedureService.updateProcedureCost(1, 2000.00);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(2000.00, result.get().getCost(), 0.01);
        verify(procedureRepository, times(1)).findById(1);
        verify(procedureRepository, times(1)).save(testProcedure);
    }

    @Test
    void updateProcedureCost_ShouldReturnEmpty_WhenProcedureNotExists() {
        // Arrange
        when(procedureRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<Procedure> result = procedureService.updateProcedureCost(1, 2000.00);

        // Assert
        assertFalse(result.isPresent());
        verify(procedureRepository, times(1)).findById(1);
        verify(procedureRepository, never()).save(any(Procedure.class));
    }

    @Test
    void updateProcedureName_ShouldUpdateSuccessfully_WhenProcedureExists() {
        // Arrange
        when(procedureRepository.findById(1)).thenReturn(Optional.of(testProcedure));
        when(procedureRepository.save(any(Procedure.class))).thenReturn(testProcedure);

        // Act
        Optional<Procedure> result = procedureService.updateProcedureName(1, "New Surgery");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("New Surgery", result.get().getName());
        verify(procedureRepository, times(1)).findById(1);
        verify(procedureRepository, times(1)).save(testProcedure);
    }

    @Test
    void updateProcedureName_ShouldReturnEmpty_WhenProcedureNotExists() {
        // Arrange
        when(procedureRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<Procedure> result = procedureService.updateProcedureName(1, "New Surgery");

        // Assert
        assertFalse(result.isPresent());
        verify(procedureRepository, times(1)).findById(1);
        verify(procedureRepository, never()).save(any(Procedure.class));
    }
}