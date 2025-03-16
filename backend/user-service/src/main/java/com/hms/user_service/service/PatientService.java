package com.hms.user_service.service;
import com.hms.user_service.model.Patient;
import com.hms.user_service.repo.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Add a new patient
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // Get all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Get patients by physicianId
    public List<Patient> getPatientsByPhysicianId(int physicianId) {
        return patientRepository.findByPhysicianId(physicianId);
    }

    // Get a specific patient by physicianId and patientId
    public Optional<Patient> getPatientByPhysicianAndPatientId(int physicianId, int patientId) {
        return patientRepository.findByPhysicianIdAndId(physicianId, patientId);
    }
}

