package com.hms.user_service.repo;

import com.hms.user_service.model.AffiliatedWith;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface AffiliatedWithRepository extends CrudRepository<AffiliatedWith, Integer> {

    // Find all affiliated physicians by department ID
    List<AffiliatedWith> findByDepartmentId(int departmentId);

    // Find all affiliations by physician ID
    List<AffiliatedWith> findByPhysicianId(String physicianId);

    // Find primary affiliation by physician ID
    Optional<AffiliatedWith> findByPhysicianIdAndIsPrimaryTrue(String physicianId);

    long countByDepartmentId(int departmentId);
}
