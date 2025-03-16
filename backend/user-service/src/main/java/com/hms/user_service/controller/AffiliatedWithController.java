package com.hms.user_service.controller;


import com.hms.user_service.model.AffiliatedWith;
import com.hms.user_service.service.AffiliatedWithService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class AffiliatedWithController {

    private final AffiliatedWithService affiliatedWithService;

    @Autowired
    public AffiliatedWithController(AffiliatedWithService affiliatedWithService) {
        this.affiliatedWithService = affiliatedWithService;
    }

    // Create a new affiliation (POST)
    @PostMapping("/affiliated_with")
    public ResponseEntity<AffiliatedWith> createAffiliation(@RequestBody AffiliatedWith affiliation) {
        AffiliatedWith createdAffiliation = affiliatedWithService.createAffiliation(affiliation);
        return ResponseEntity.ok(createdAffiliation);
    }

    // Get all physicians affiliated with a department (GET)
    @GetMapping("/affiliated_with/physicians/{deptid}")
    public ResponseEntity<List<AffiliatedWith>> getPhysiciansByDepartment(@PathVariable int deptid) {
        List<AffiliatedWith> affiliations = affiliatedWithService.getPhysiciansByDepartmentId(deptid);
        return ResponseEntity.ok(affiliations);
    }

    // Get all departments affiliated with a physician (GET)
    @GetMapping("/affiliated_with/department/{physicianid}")
    public ResponseEntity<List<AffiliatedWith>> getDepartmentsByPhysician(@PathVariable String physicianid) {
        List<AffiliatedWith> affiliations = affiliatedWithService.getDepartmentsByPhysicianId(physicianid);
        return ResponseEntity.ok(affiliations);
    }

    // Get the count of physicians affiliated with a department (GET)
    @GetMapping("/affiliated_with/countphysician/{deptid}")
    public ResponseEntity<Long> countPhysiciansInDepartment(@PathVariable int deptid) {
        long count = affiliatedWithService.countPhysiciansInDepartment(deptid);
        return ResponseEntity.ok(count);
    }

    // Get the primary affiliation for a physician (GET)
    @GetMapping("/affiliated_with/primary/{physicianid}")
    public ResponseEntity<AffiliatedWith> getPrimaryAffiliation(@PathVariable String physicianid) {
        Optional<AffiliatedWith> affiliation = affiliatedWithService.getPrimaryAffiliation(physicianid);
        return affiliation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
