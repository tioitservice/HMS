package com.hms.user_service.controller;

import com.hms.user_service.model.TrainedIn;
import com.hms.user_service.service.TrainedInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class TrainedInController {

    private final TrainedInService trainedInService;

    @Autowired
    public TrainedInController(TrainedInService trainedInService) {
        this.trainedInService = trainedInService;
    }

    // POST: Add a certification to the DB
    @PostMapping("/trained_in")
    public ResponseEntity<String> addCertification(@RequestBody TrainedIn trainedIn) {
        trainedInService.addCertification(trainedIn);
        return ResponseEntity.ok("Record Created Successfully");
    }

    // GET: Get a list of all trained procedures available with certification
    @GetMapping("/trained_in")
    public ResponseEntity<List<TrainedIn>> getAllTrainedProcedures() {
        List<TrainedIn> trainedProcedures = trainedInService.getAllTrainedProcedures();
        return ResponseEntity.ok(trainedProcedures);
    }

    // GET: Get a list of treatments a physician can perform based on their certifications
    @GetMapping("/trained_in/treatment/{physicianid}")
    public ResponseEntity<List<TrainedIn>> getTreatmentsByPhysician(@PathVariable int physicianid) {
        List<TrainedIn> treatments = trainedInService.getTreatmentsByPhysicianId(physicianid);
        return ResponseEntity.ok(treatments);
    }

    // GET: Get a list of physicians certified for a particular procedure
    @GetMapping("/trained_in/physicians/{procedureid}")
    public ResponseEntity<List<TrainedIn>> getPhysiciansForProcedure(@PathVariable int procedureid) {
        List<TrainedIn> physicians = trainedInService.getPhysiciansForProcedure(procedureid);
        return ResponseEntity.ok(physicians);
    }

    // GET: Get a list of procedures whose certifications will expire within a month
    @GetMapping("/trained_in/expiredsooncerti/{physicianid}")
    public ResponseEntity<List<TrainedIn>> getProceduresExpiringSoon(@PathVariable int physicianid) {
        List<TrainedIn> expiringProcedures = trainedInService.getProceduresExpiringSoon(physicianid);
        return ResponseEntity.ok(expiringProcedures);
    }

    // PUT: Update the certification expiry date for a particular physician and procedure
    @PutMapping("/trained_in/certificationexpiry/{physicianid}/{procedureid}")
    public ResponseEntity<Boolean> updateCertificationExpiry(
            @PathVariable int physicianid, @PathVariable int procedureid, @RequestBody LocalDate newExpiryDate) {
        boolean isUpdated = trainedInService.updateCertificationExpiry(physicianid, procedureid, newExpiryDate);
        return ResponseEntity.ok(isUpdated);
    }
}
