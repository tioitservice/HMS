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

    public Physician updatePosition(String position, int employeeId) {
        Optional<Physician> physician = physicianRepository.findByEmployeeId(employeeId);
        if (physician.isPresent()) {
            physician.get().setPosition(position);
            return physicianRepository.save(physician.get());
        }
        return null;
    }

    public Physician updateName(int employeeId, String name) {
        Optional<Physician> physician = physicianRepository.findByEmployeeId(employeeId);
        if (physician.isPresent()) {
            physician.get().setName(name);
            return physicianRepository.save(physician.get());
        }
        return null;
    }
    public List<Physician> getAllPhysicians() {
        return physicianRepository.findAll();
    }
}
