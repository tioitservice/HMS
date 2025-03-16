package com.hms.user_service.service;

import com.hms.user_service.model.Nurse;
import com.hms.user_service.repo.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NurseService {

    private final NurseRepository nurseRepository;

    @Autowired
    public NurseService(NurseRepository nurseRepository) {
        this.nurseRepository = nurseRepository;
    }

    // Add a new nurse
    public Nurse addNurse(Nurse nurse) {
        return nurseRepository.save(nurse);
    }

    // Get all nurses
    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }

    // Get a nurse by employee ID
    public Optional<Nurse> getNurseByEmpId(int empId) {
        return nurseRepository.findByEmpId(empId);
    }

    // Get the position of a nurse by employee ID
    public Optional<String> getNursePosition(int empId) {
        return nurseRepository.findPositionByEmpId(empId);
    }

    // Get if the nurse is registered by employee ID
    public Optional<Boolean> isNurseRegistered(int empId) {
        return nurseRepository.findIsRegisteredByEmpId(empId);
    }

    // Update the registration status of the nurse
    public Optional<Nurse> updateNurseRegistration(int empId, boolean isRegistered) {
        Optional<Nurse> nurse = nurseRepository.findByEmpId(empId);
        if (nurse.isPresent()) {
            Nurse updatedNurse = nurse.get();
            updatedNurse.setRegistered(isRegistered);
            return Optional.of(nurseRepository.save(updatedNurse));
        }
        return Optional.empty();
    }
}
