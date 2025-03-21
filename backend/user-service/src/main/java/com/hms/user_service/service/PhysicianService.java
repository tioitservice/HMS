package com.hms.user_service.service;

import com.hms.user_service.model.Physician;
import com.hms.user_service.repo.PhysicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhysicianService {

    private final PhysicianRepository physicianRepository;

    @Autowired
    public PhysicianService(PhysicianRepository physicianRepository) {
        this.physicianRepository = physicianRepository;
    }

    public Physician createPhysician(Physician physician) {
        return physicianRepository.save(physician);
    }

    public Optional<Physician> getPhysicianByName(String name) {
        return physicianRepository.findByName(name);
    }

    public Optional<Physician> getPhysicianByPosition(String position) {
        return physicianRepository.findByPosition(position);
    }

    public Optional<Physician> getPhysicianByEmployeeId(int employeeId) {
        return physicianRepository.findByEmployeeId(employeeId);
    }

    // New method to find a physician by SSN
    public Optional<Physician> getPhysicianBySsn(String ssn) {
        return physicianRepository.findBySsn(ssn);
    }

    // Update position method
    public Physician updatePosition(String position, int employeeId) {
        Optional<Physician> physician = physicianRepository.findByEmployeeId(employeeId);
        if (physician.isPresent()) {
            physician.get().setPosition(position);
            return physicianRepository.save(physician.get());
        }
        return null;
    }

    // Update name method
    public Physician updateName(int employeeId, Physician physician) {
        Optional<Physician> exphysician = physicianRepository.findByEmployeeId(employeeId);
        if (exphysician.isPresent()) {
            exphysician.get().setName(physician.getName());
            return physicianRepository.save(exphysician.get());
        }
        return null;
    }

    // Update SSN method
    public Physician updateSsn(String newSsn, int employeeId) {
        Optional<Physician> physician = physicianRepository.findByEmployeeId(employeeId);
        if (physician.isPresent()) {
            physician.get().setSsn(newSsn);  // Update the SSN
            return physicianRepository.save(physician.get());
        }
        return null;
    }

    // Update physician information method
    public Physician updatePhysician(int empid, Physician physician) {
        // Check if the physician exists
        Physician existingPhysician = physicianRepository.findById(empid).orElse(null);
        if (existingPhysician == null) {
            return null;
        }
        existingPhysician.setName(physician.getName());
        existingPhysician.setPosition(physician.getPosition());
        existingPhysician.setDeptId(physician.getDeptId());
        existingPhysician.setTrainId(physician.getTrainId());

        return physicianRepository.save(existingPhysician);
    }

    // Get all physicians method
    public List<Physician> getAllPhysicians() {
        return physicianRepository.findAll();
    }
}
