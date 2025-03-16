package com.hms.user_service.controller;

import com.hms.user_service.model.Patient;
import com.hms.user_service.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // Add a patient report
    @PostMapping("/patient")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        Patient createdPatient = patientService.createPatient(patient);
        return ResponseEntity.ok(createdPatient);
    }

    // Get all patients
    @GetMapping("/patient")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    // Get patients by physicianId
    @GetMapping("/patient/{physicianid}")
    public ResponseEntity<List<Patient>> getPatientsByPhysician(@PathVariable int physicianid) {
        List<Patient> patients = patientService.getPatientsByPhysicianId(physicianid);
        return patients.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(patients);
    }

    // Get patient details by physicianId and patientId
    @GetMapping("/patient/{physicianid}/{patientid}")
    public ResponseEntity<Patient> getPatientDetails(@PathVariable int physicianid, @PathVariable int patientid) {
        Optional<Patient> patient = patientService.getPatientByPhysicianAndPatientId(physicianid, patientid);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

