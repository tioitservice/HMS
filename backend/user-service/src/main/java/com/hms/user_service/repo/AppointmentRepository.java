package com.hms.user_service.repo;

import com.hms.user_service.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByStartDate(Date startDate);
    List<Appointment> findByPatientId(int patientId);
    List<Appointment> findByPhysicianId(int physicianId);
    List<Appointment> findByNurseId(int nurseId);
    List<Appointment> findByExaminationRoomId(int roomId);
    List<Appointment> findByPhysicianIdAndStartDate(int physicianId, Date date);
    List<Appointment> findByNurseIdAndStartDate(int nurseId, Date date);
    List<Appointment> findByPatientIdAndStartDate(int patientId, Date date);
    List<Appointment> findByExaminationRoomIdAndStartDate(int roomId, Date date); // Added method

    @Query("SELECT a.startDate FROM Appointment a WHERE a.patientId = :patientId")
    List<Date> findDatesByPatientId(int patientId);

    Optional<Appointment> findByPhysicianIdAndPatientId(int physicianId, int patientId);
    Optional<Appointment> findByNurseIdAndPatientId(int nurseId, int patientId);
}