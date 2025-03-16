package com.hms.user_service.service;


import com.hms.user_service.model.AffiliatedWith;
import com.hms.user_service.repo.AffiliatedWithRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AffiliatedWithService {

    private final AffiliatedWithRepository affiliatedWithRepository;

    @Autowired
    public AffiliatedWithService(AffiliatedWithRepository affiliatedWithRepository) {
        this.affiliatedWithRepository = affiliatedWithRepository;
    }

    // Create new affiliation
    public AffiliatedWith createAffiliation(AffiliatedWith affiliation) {
        return affiliatedWithRepository.save(affiliation);
    }

    // Get physicians affiliated with a department
    public List<AffiliatedWith> getPhysiciansByDepartmentId(int deptId) {
        return affiliatedWithRepository.findByDepartmentId(deptId);
    }

    // Get department affiliated with a physician
    public List<AffiliatedWith> getDepartmentsByPhysicianId(String physicianId) {
        return affiliatedWithRepository.findByPhysicianId(physicianId);
    }

    // Get primary affiliation for a physician
    public Optional<AffiliatedWith> getPrimaryAffiliation(String physicianId) {
        return affiliatedWithRepository.findByPhysicianIdAndIsPrimaryTrue(physicianId);
    }

    // Get the count of physicians affiliated with a department
    public long countPhysiciansInDepartment(int deptId) {
        return affiliatedWithRepository.countByDepartmentId(deptId);
    }
}
