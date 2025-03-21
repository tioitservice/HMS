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

    public Nurse addNurse(Nurse nurse) {
        return nurseRepository.save(nurse);
    }

    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }

    public Optional<Nurse> getNurseByEmpId(int empId) {
        return nurseRepository.findByEmpId(empId);
    }

    public Optional<String> getNursePosition(int empId) {
        return nurseRepository.findPositionByEmpId(empId);
    }

    public Optional<Boolean> isNurseRegistered(int empId) {
        return nurseRepository.findIsRegisteredByEmpId(empId);
    }

    public Optional<Nurse> updateNurse(int empId, Nurse nurse) {
        Optional<Nurse> existingNurse = nurseRepository.findByEmpId(empId);
        if (existingNurse.isPresent()) {
            Nurse updatedNurse = existingNurse.get();

            updatedNurse.setName(nurse.getName());
            updatedNurse.setPosition(nurse.getPosition());
            updatedNurse.setRegistered(nurse.isRegistered());
            updatedNurse.setSsn(nurse.getSsn()); // Update SSN

            return Optional.of(nurseRepository.save(updatedNurse));
        }
        return Optional.empty();
    }
}
