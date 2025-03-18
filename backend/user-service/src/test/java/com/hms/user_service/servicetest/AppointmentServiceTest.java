package com.hms.user_service.servicetest;

import com.hms.user_service.model.Appointment;
import com.hms.user_service.repo.AppointmentRepository;
import com.hms.user_service.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment testAppointment;
    private Date testDate;

    @BeforeEach
    void setUp() {
        testDate = new Date();
        testAppointment = new Appointment();
        testAppointment.setPhysicianId(200);
        testAppointment.setNurseId(300);
        testAppointment.setStartDate(testDate);
        testAppointment.setExaminationRoomId(400);
    }

    @Test
    void addAppointment_ShouldSaveAppointmentSuccessfully() {
        // Arrange
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);

        // Act
        Appointment result = appointmentService.addAppointment(testAppointment);

        // Assert
        assertNotNull(result);
        assertEquals(100, result.getPatientId());
        assertEquals(200, result.getPhysicianId());
        assertEquals(300, result.getNurseId());
        assertEquals(testDate, result.getStartDate());
        assertEquals(400, result.getExaminationRoomId());
        verify(appointmentRepository, times(1)).save(testAppointment);
    }

    @Test
    void getAllAppointments_ShouldReturnAllAppointments() {
        // Arrange
        List<Appointment> appointments = Arrays.asList(testAppointment);
        when(appointmentRepository.findAll()).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.getAllAppointments();

        // Assert
        assertEquals(1, result.size());
        assertEquals(testAppointment, result.get(0));
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void getAppointmentsByStartDate_ShouldReturnAppointments() {
        // Arrange
        List<Appointment> appointments = Arrays.asList(testAppointment);
        when(appointmentRepository.findByStartDate(testDate)).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.getAppointmentsByStartDate(testDate);

        // Assert
        assertEquals(1, result.size());
        assertEquals(testAppointment, result.get(0));
        verify(appointmentRepository, times(1)).findByStartDate(testDate);
    }

    @Test
    void getAppointmentById_ShouldReturnAppointment_WhenExists() {
        // Arrange
        when(appointmentRepository.findById(1)).thenReturn(Optional.of(testAppointment));

        // Act
        Optional<Appointment> result = appointmentService.getAppointmentById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testAppointment, result.get());
        verify(appointmentRepository, times(1)).findById(1);
    }

    @Test
    void getAppointmentsByPatientId_ShouldReturnAppointments() {
        // Arrange
        List<Appointment> appointments = Arrays.asList(testAppointment);
        when(appointmentRepository.findByPatientId(100)).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.getAppointmentsByPatientId(100);

        // Assert
        assertEquals(1, result.size());
        assertEquals(testAppointment, result.get(0));
        verify(appointmentRepository, times(1)).findByPatientId(100);
    }

    @Test
    void getAppointmentsByPhysicianId_ShouldReturnAppointments() {
        // Arrange
        List<Appointment> appointments = Arrays.asList(testAppointment);
        when(appointmentRepository.findByPhysicianId(200)).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.getAppointmentsByPhysicianId(200);

        // Assert
        assertEquals(1, result.size());
        assertEquals(testAppointment, result.get(0));
        verify(appointmentRepository, times(1)).findByPhysicianId(200);
    }

    @Test
    void getAppointmentsByNurseId_ShouldReturnAppointments() {
        // Arrange
        List<Appointment> appointments = Arrays.asList(testAppointment);
        when(appointmentRepository.findByNurseId(300)).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.getAppointmentsByNurseId(300);

        // Assert
        assertEquals(1, result.size());
        assertEquals(testAppointment, result.get(0));
        verify(appointmentRepository, times(1)).findByNurseId(300);
    }

    @Test
    void getAppointmentsByRoomId_ShouldReturnAppointments() {
        // Arrange
        List<Appointment> appointments = Arrays.asList(testAppointment);
        when(appointmentRepository.findByExaminationRoomId(400)).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.getAppointmentsByRoomId(400);

        // Assert
        assertEquals(1, result.size());
        assertEquals(testAppointment, result.get(0));
        verify(appointmentRepository, times(1)).findByExaminationRoomId(400);
    }

    @Test
    void getAppointmentsByPhysicianIdAndDate_ShouldReturnAppointments() {
        // Arrange
        List<Appointment> appointments = Arrays.asList(testAppointment);
        when(appointmentRepository.findByPhysicianIdAndStartDate(200, testDate)).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.getAppointmentsByPhysicianIdAndDate(200, testDate);

        // Assert
        assertEquals(1, result.size());
        assertEquals(testAppointment, result.get(0));
        verify(appointmentRepository, times(1)).findByPhysicianIdAndStartDate(200, testDate);
    }

    @Test
    void getAppointmentsByNurseIdAndDate_ShouldReturnAppointments() {
        // Arrange
        List<Appointment> appointments = Arrays.asList(testAppointment);
        when(appointmentRepository.findByNurseIdAndStartDate(300, testDate)).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.getAppointmentsByNurseIdAndDate(300, testDate);

        // Assert
        assertEquals(1, result.size());
        assertEquals(testAppointment, result.get(0));
        verify(appointmentRepository, times(1)).findByNurseIdAndStartDate(300, testDate);
    }

    @Test
    void getAppointmentsByPatientIdAndDate_ShouldReturnAppointments() {
        // Arrange
        List<Appointment> appointments = Arrays.asList(testAppointment);
        when(appointmentRepository.findByPatientIdAndStartDate(100, testDate)).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.getAppointmentsByPatientIdAndDate(100, testDate);

        // Assert
        assertEquals(1, result.size());
        assertEquals(testAppointment, result.get(0));
        verify(appointmentRepository, times(1)).findByPatientIdAndStartDate(100, testDate);
    }

    @Test
    void getAppointmentDatesByPatientId_ShouldReturnDates() {
        // Arrange
        List<Date> dates = Arrays.asList(testDate);
        when(appointmentRepository.findDatesByPatientId(100)).thenReturn(dates);

        // Act
        List<Date> result = appointmentService.getAppointmentDatesByPatientId(100);

        // Assert
        assertEquals(1, result.size());
        assertEquals(testDate, result.get(0));
        verify(appointmentRepository, times(1)).findDatesByPatientId(100);
    }

    @Test
    void getAppointmentByPhysicianIdAndPatientId_ShouldReturnAppointment_WhenExists() {
        // Arrange
        when(appointmentRepository.findByPhysicianIdAndPatientId(200, 100))
                .thenReturn(Optional.of(testAppointment));

        // Act
        Optional<Appointment> result = appointmentService.getAppointmentByPhysicianIdAndPatientId(200, 100);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testAppointment, result.get());
        verify(appointmentRepository, times(1)).findByPhysicianIdAndPatientId(200, 100);
    }

    @Test
    void getAppointmentByNurseIdAndPatientId_ShouldReturnAppointment_WhenExists() {
        // Arrange
        when(appointmentRepository.findByNurseIdAndPatientId(300, 100))
                .thenReturn(Optional.of(testAppointment));

        // Act
        Optional<Appointment> result = appointmentService.getAppointmentByNurseIdAndPatientId(300, 100);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testAppointment, result.get());
        verify(appointmentRepository, times(1)).findByNurseIdAndPatientId(300, 100);
    }

    @Test
    void updateExaminationRoom_ShouldUpdateSuccessfully_WhenAppointmentExists() {
        // Arrange
        when(appointmentRepository.findById(1)).thenReturn(Optional.of(testAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);

        // Act
        Optional<Appointment> result = appointmentService.updateExaminationRoom(1, 500);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(500, result.get().getExaminationRoomId());
        verify(appointmentRepository, times(1)).findById(1);
        verify(appointmentRepository, times(1)).save(testAppointment);
    }

    @Test
    void updateExaminationRoom_ShouldReturnEmpty_WhenAppointmentNotExists() {
        // Arrange
        when(appointmentRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<Appointment> result = appointmentService.updateExaminationRoom(1, 500);

        // Assert
        assertFalse(result.isPresent());
        verify(appointmentRepository, times(1)).findById(1);
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }
}