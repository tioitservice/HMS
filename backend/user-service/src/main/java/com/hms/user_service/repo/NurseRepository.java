package com.hms.user_service.repo;

import com.hms.user_service.model.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Integer> {

    // Custom query to find a nurse by empId
    Optional<Nurse> findByEmpId(int empId);

    // Custom query to find the position of a nurse by empId
    Optional<String> findPositionByEmpId(int empId);

    // Custom query to find if the nurse is registered by empId
    Optional<Boolean> findIsRegisteredByEmpId(int empId);
}
