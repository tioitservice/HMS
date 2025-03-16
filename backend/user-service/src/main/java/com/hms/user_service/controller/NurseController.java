package com.hms.user_service.controller;

import com.hms.user_service.model.Nurse;
import com.hms.user_service.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class NurseController {

    private final NurseService nurseService;

    @Autowired
    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    // Add a nurse detail to the DB
    @PostMapping("/nurse")
    public ResponseEntity<String> addNurse(@RequestBody Nurse nurse) {
        nurseService.addNurse(nurse);
        return ResponseEntity.ok("Record Created Successfully");
    }

    // Get a list of all nurses
    @GetMapping("/nurse")
    public ResponseEntity<List<Nurse>> getAllNurses() {
        List<Nurse> nurses = nurseService.getAllNurses();
        return ResponseEntity.ok(nurses);
    }

    // Get a nurse detail by empId
    @GetMapping("/nurse/{empid}")
    public ResponseEntity<Nurse> getNurseByEmpId(@PathVariable int empid) {
        Optional<Nurse> nurse = nurseService.getNurseByEmpId(empid);
        return nurse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get position of nurse by empId
    @GetMapping("/nurse/position/{empid}")
    public ResponseEntity<String> getNursePosition(@PathVariable int empid) {
        Optional<String> position = nurseService.getNursePosition(empid);
        if (position.isPresent()) {
            return ResponseEntity.ok(position.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Get if the nurse is registered by empId
    @GetMapping("/nurse/registered/{empid}")
    public ResponseEntity<Boolean> isNurseRegistered(@PathVariable int empid) {
        Optional<Boolean> isRegistered = nurseService.isNurseRegistered(empid);
        if (isRegistered.isPresent()) {
            return ResponseEntity.ok(isRegistered.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Update the registered value of nurse by empId
    @PutMapping("/registered/{empid}")
    public ResponseEntity<Nurse> updateNurseRegistration(@PathVariable int empid, @RequestBody boolean isRegistered) {
        Optional<Nurse> updatedNurse = nurseService.updateNurseRegistration(empid, isRegistered);
        if (updatedNurse.isPresent()) {
            return ResponseEntity.ok(updatedNurse.get());
        }
        return ResponseEntity.notFound().build();    }
}
