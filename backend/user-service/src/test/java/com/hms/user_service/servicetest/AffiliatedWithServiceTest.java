package com.hms.user_service.servicetest;

import com.hms.user_service.model.AffiliatedWith;
import com.hms.user_service.repo.AffiliatedWithRepository;
import com.hms.user_service.service.AffiliatedWithService;
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
class AffiliatedWithServiceTest {

    @Mock
    private AffiliatedWithRepository affiliatedWithRepository;

    @InjectMocks
    private AffiliatedWithService affiliatedWithService;

    private AffiliatedWith testAffiliation;

    @BeforeEach
    void setUp() {
        testAffiliation = new AffiliatedWith();
        testAffiliation.setPhysicianId("phy123");
        testAffiliation.setDepartmentId(1l);
        testAffiliation.setPrimary(true);
    }

    @Test
    void createAffiliation_ShouldSaveAffiliationSuccessfully() {
        // Arrange
        when(affiliatedWithRepository.save(any(AffiliatedWith.class))).thenReturn(testAffiliation);

        // Act
        AffiliatedWith result = affiliatedWithService.createAffiliation(testAffiliation);

        // Assert
        assertNotNull(result);
        assertEquals("phy123", result.getPhysicianId());
        assertEquals(1, result.getDepartmentId());
        assertTrue(result.isPrimary());
        verify(affiliatedWithRepository, times(1)).save(testAffiliation);
    }

    @Test
    void getPhysiciansByDepartmentId_ShouldReturnAffiliations() {
        // Arrange
        List<AffiliatedWith> affiliations = Arrays.asList(testAffiliation);
        when(affiliatedWithRepository.findByDepartmentId(1)).thenReturn(affiliations);

        // Act
        List<AffiliatedWith> result = affiliatedWithService.getPhysiciansByDepartmentId(1);

        // Assert
        assertEquals(1, result.size());
        assertEquals(testAffiliation, result.get(0));
        verify(affiliatedWithRepository, times(1)).findByDepartmentId(1);
    }

    @Test
    void getDepartmentsByPhysicianId_ShouldReturnAffiliations() {
        // Arrange
        List<AffiliatedWith> affiliations = Arrays.asList(testAffiliation);
        when(affiliatedWithRepository.findByPhysicianId("phy123")).thenReturn(affiliations);

        // Act
        List<AffiliatedWith> result = affiliatedWithService.getDepartmentsByPhysicianId("phy123");

        // Assert
        assertEquals(1, result.size());
        assertEquals(testAffiliation, result.get(0));
        verify(affiliatedWithRepository, times(1)).findByPhysicianId("phy123");
    }

    @Test
    void getPrimaryAffiliation_ShouldReturnPrimaryAffiliation_WhenExists() {
        // Arrange
        when(affiliatedWithRepository.findByPhysicianIdAndIsPrimaryTrue("phy123"))
                .thenReturn(Optional.of(testAffiliation));

        // Act
        Optional<AffiliatedWith> result = affiliatedWithService.getPrimaryAffiliation("phy123");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testAffiliation, result.get());
        verify(affiliatedWithRepository, times(1)).findByPhysicianIdAndIsPrimaryTrue("phy123");
    }

    @Test
    void getPrimaryAffiliation_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        when(affiliatedWithRepository.findByPhysicianIdAndIsPrimaryTrue("phy123"))
                .thenReturn(Optional.empty());

        // Act
        Optional<AffiliatedWith> result = affiliatedWithService.getPrimaryAffiliation("phy123");

        // Assert
        assertFalse(result.isPresent());
        verify(affiliatedWithRepository, times(1)).findByPhysicianIdAndIsPrimaryTrue("phy123");
    }

    @Test
    void countPhysiciansInDepartment_ShouldReturnCorrectCount() {
        // Arrange
        when(affiliatedWithRepository.countByDepartmentId(1)).thenReturn(5L);

        // Act
        long result = affiliatedWithService.countPhysiciansInDepartment(1);

        // Assert
        assertEquals(5L, result);
        verify(affiliatedWithRepository, times(1)).countByDepartmentId(1);
    }

    @Test
    void countPhysiciansInDepartment_ShouldReturnZero_WhenNoAffiliations() {
        // Arrange
        when(affiliatedWithRepository.countByDepartmentId(1)).thenReturn(0L);

        // Act
        long result = affiliatedWithService.countPhysiciansInDepartment(1);

        // Assert
        assertEquals(0L, result);
        verify(affiliatedWithRepository, times(1)).countByDepartmentId(1);
    }
}
