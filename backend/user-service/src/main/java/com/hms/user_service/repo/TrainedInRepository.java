package com.hms.user_service.repo;

import com.hms.user_service.model.TrainedIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainedInRepository extends JpaRepository<TrainedIn, Integer> {

    // Find trained procedures for a given physician
    List<TrainedIn> findByPhysicianId(int physicianId);

    // Find trained physicians for a given procedure
    List<TrainedIn> findByProcedureId(int procedureId);

    // Find all trained procedures that will expire soon (within a month)
    List<TrainedIn> findByPhysicianIdAndCertificationExpiryDateBetween(int physicianId,
                                                                       LocalDate startDate, LocalDate endDate);
}
