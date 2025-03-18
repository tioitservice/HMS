package com.hms.user_service.service;


import com.hms.user_service.model.Procedure;
import com.hms.user_service.model.Physician;
import com.hms.user_service.model.TrainedIn;
import com.hms.user_service.repo.TrainedInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TrainedInService {

    private final TrainedInRepository trainedInRepository;

    @Autowired
    public TrainedInService(TrainedInRepository trainedInRepository) {
        this.trainedInRepository = trainedInRepository;
    }

    // Add a certification
    public TrainedIn addTrainedIn(TrainedIn trainedIn) {
        return trainedInRepository.save(trainedIn);
    }

    // Get all trained procedures
    public List<TrainedIn> getAllTrainedProcedures() {
        return trainedInRepository.findAll();
    }

    public boolean deleteTrainedIn(int id) {
        Optional<TrainedIn> trainedIn = trainedInRepository.findById(id);
        if (trainedIn.isPresent()) {
            trainedInRepository.delete(trainedIn.get());
            return true; // Successfully deleted
        } else {
            return false; // Item not found
        }
    }
    // Get a list of procedures that a physician can treat
//    public List<TrainedIn> getTreatmentsByPhysicianId(int physicianId) {
//        return trainedInRepository.findByPhysicianId(physicianId);
//    }

    // Get a list of physicians trained for a particular treatment
//    public List<TrainedIn> getPhysiciansForProcedure(int procedureId) {
//        return trainedInRepository.findByProcedureId(procedureId);
//    }

    // Get procedures whose certification is about to expire within a month
//    public List<TrainedIn> getProceduresExpiringSoon(int physicianId) {
//        LocalDate currentDate = LocalDate.now();
//        LocalDate expiryDateLimit = currentDate.plusMonths(1);
//        return trainedInRepository.findByPhysicianIdAndCertificationExpiryDateBetween(
//                physicianId, currentDate, expiryDateLimit);
//    }

    // Update the certification expiry date
//    public boolean updateCertificationExpiry(int physicianId, int procedureId, LocalDate newExpiryDate) {
//        Optional<TrainedIn> trainedIn = trainedInRepository.findById(physicianId);
//        if (trainedIn.isPresent()) {
//            TrainedIn certification = trainedIn.get();
//            if (certification.get()==procedureId) {
//                certification.setCertificationExpiryDate(newExpiryDate);
//                trainedInRepository.save(certification);
//                return true;
//            }
//        }
//        return false;
//    }
}
