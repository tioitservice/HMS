package com.hms.user_service.servicetest;


import com.hms.user_service.model.Department;
import com.hms.user_service.repo.DepartmentRepository;
import com.hms.user_service.service.DepartmentService;
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
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department testDepartment;

    @BeforeEach
    void setUp() {
        testDepartment = new Department();
        testDepartment.setDeptId(1);
        testDepartment.setDeptName("Engineering");
        testDepartment.setHeadId("head123");
        testDepartment.setHeadName("John Doe");
    }

    @Test
    void createDepartment_ShouldSaveDepartmentSuccessfully() {
        // Arrange
        when(departmentRepository.save(any(Department.class))).thenReturn(testDepartment);

        // Act
        Department result = departmentService.createDepartment(testDepartment);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getDeptId());
        assertEquals("Engineering", result.getDeptName());
        assertEquals("head123", result.getHeadId());
        verify(departmentRepository, times(1)).save(testDepartment);
    }

    @Test
    void getDepartmentById_ShouldReturnDepartment_WhenExists() {
        // Arrange
        when(departmentRepository.findByDeptId(1)).thenReturn(Optional.of(testDepartment));

        // Act
        Optional<Department> result = departmentService.getDepartmentById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testDepartment, result.get());
        verify(departmentRepository, times(1)).findByDeptId(1);
    }

    @Test
    void getDepartmentById_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        when(departmentRepository.findByDeptId(1)).thenReturn(Optional.empty());

        // Act
        Optional<Department> result = departmentService.getDepartmentById(1);

        // Assert
        assertFalse(result.isPresent());
        verify(departmentRepository, times(1)).findByDeptId(1);
    }

    @Test
    void getDepartmentByName_ShouldReturnDepartment_WhenExists() {
        // Arrange
        when(departmentRepository.findByDeptName("Engineering")).thenReturn(Optional.of(testDepartment));

        // Act
        Optional<Department> result = departmentService.getDepartmentByName("Engineering");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testDepartment, result.get());
        verify(departmentRepository, times(1)).findByDeptName("Engineering");
    }

    @Test
    void getDepartmentByHeadId_ShouldReturnDepartment_WhenExists() {
        // Arrange
        when(departmentRepository.findByHeadId("head123")).thenReturn(Optional.of(testDepartment));

        // Act
        Optional<Department> result = departmentService.getDepartmentByHeadId("head123");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testDepartment, result.get());
        verify(departmentRepository, times(1)).findByHeadId("head123");
    }

    @Test
    void getAllDepartment_ShouldReturnAllDepartments() {
        // Arrange
        List<Department> departments = Arrays.asList(testDepartment);
        when(departmentRepository.findAll()).thenReturn(departments);

        // Act
        List<Department> result = departmentService.getAllDepartment();

        // Assert
        assertEquals(1, result.size());
        assertEquals(testDepartment, result.get(0));
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void getDepartmentByHeadName_ShouldReturnDepartment_WhenExists() {
        // Arrange
        when(departmentRepository.findByHeadName("John Doe")).thenReturn(Optional.of(testDepartment));

        // Act
        Optional<Department> result = departmentService.getDepartmentByHeadName("John Doe");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testDepartment, result.get());
        verify(departmentRepository, times(1)).findByHeadName("John Doe");
    }

    @Test
    void updateDepartmentHeadId_ShouldUpdateSuccessfully_WhenDepartmentExists() {
        // Arrange
        when(departmentRepository.findByDeptId(1)).thenReturn(Optional.of(testDepartment));
        when(departmentRepository.save(any(Department.class))).thenReturn(testDepartment);

        // Act
        Department result = departmentService.updateDepartmentHeadId(1, "newHead456");

        // Assert
        assertNotNull(result);
        assertEquals("newHead456", result.getHeadId());
        verify(departmentRepository, times(1)).findByDeptId(1);
        verify(departmentRepository, times(1)).save(testDepartment);
    }

    @Test
    void updateDepartmentHeadId_ShouldReturnNull_WhenDepartmentNotExists() {
        // Arrange
        when(departmentRepository.findByDeptId(1)).thenReturn(Optional.empty());

        // Act
        Department result = departmentService.updateDepartmentHeadId(1, "newHead456");

        // Assert
        assertNull(result);
        verify(departmentRepository, times(1)).findByDeptId(1);
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    void updateDepartmentName_ShouldUpdateSuccessfully_WhenDepartmentExists() {
        // Arrange
        when(departmentRepository.findByDeptId(1)).thenReturn(Optional.of(testDepartment));
        when(departmentRepository.save(any(Department.class))).thenReturn(testDepartment);

        // Act
        Department result = departmentService.updateDepartmentName(1, "NewDeptName");

        // Assert
        assertNotNull(result);
        assertEquals("NewDeptName", result.getDeptName());
        verify(departmentRepository, times(1)).findByDeptId(1);
        verify(departmentRepository, times(1)).save(testDepartment);
    }

    @Test
    void updateDepartmentName_ShouldReturnNull_WhenDepartmentNotExists() {
        // Arrange
        when(departmentRepository.findByDeptId(1)).thenReturn(Optional.empty());

        // Act
        Department result = departmentService.updateDepartmentName(1, "NewDeptName");

        // Assert
        assertNull(result);
        verify(departmentRepository, times(1)).findByDeptId(1);
        verify(departmentRepository, never()).save(any(Department.class));
    }
}