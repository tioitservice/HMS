package com.hms.user_service.repo;

import com.hms.user_service.model.Physician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhysicianRepository extends JpaRepository<Physician, Integer> {

    Optional<Physician> findByName(String name);

    Optional<Physician> findByPosition(String position);

    Optional<Physician> findByEmployeeId(int employeeId);

    Optional<Physician> findBySsn(String ssn);
}
