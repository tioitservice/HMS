package com.hms.user_service.repo;

import com.hms.user_service.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    // Custom query to find patients by physicianId
    List<Patient> findByPhysicianId(int physicianId);

    // Custom query to find a patient by physicianId and patientId
    Optional<Patient> findByPhysicianIdAndId(int physicianId, int patientId);
}

