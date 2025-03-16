package com.hms.user_service.controller;
import com.hms.user_service.model.Physician;
import com.hms.user_service.service.PhysicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class PhysicianController {

    private final PhysicianService physicianService;

    @Autowired
    public PhysicianController(PhysicianService physicianService) {
        this.physicianService = physicianService;
    }

    // Create a new Physician
    @PostMapping("/physician")
    public ResponseEntity<Physician> createPhysician(@RequestBody Physician physician) {
        Physician createdPhysician = physicianService.createPhysician(physician);
        return ResponseEntity.ok(createdPhysician);
    }

    // Get Physician by Name
    @GetMapping("/physician/name/{name}")
    public ResponseEntity<Physician> getPhysicianByName(@PathVariable String name) {
        Optional<Physician> physician = physicianService.getPhysicianByName(name);
        return physician.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/physicians")
    public List<Physician> getAllPhysicians() {
        return physicianService.getAllPhysicians();
    }
    // Get Physician by Position
    @GetMapping("/physician/position/{pos}")
    public ResponseEntity<Physician> getPhysicianByPosition(@PathVariable String pos) {
        Optional<Physician> physician = physicianService.getPhysicianByPosition(pos);
        return physician.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get Physician by Employee ID
    @GetMapping("/physician/empid/{empid}")
    public ResponseEntity<Physician> getPhysicianByEmployeeId(@PathVariable int empid) {
        Optional<Physician> physician = physicianService.getPhysicianByEmployeeId(empid);
        return physician.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update Physician's Position
    @PutMapping("/physician/update/position/{position}/{employeeId}")
    public ResponseEntity<Physician> updatePosition(@PathVariable String position, @PathVariable int employeeId) {
        Physician updatedPhysician = physicianService.updatePosition(position, employeeId);
        return updatedPhysician != null ? ResponseEntity.ok(updatedPhysician) : ResponseEntity.notFound().build();
    }

    // Update Physician's Name
    @PutMapping("/physician/update/name/{empid}")
    public ResponseEntity<Physician> updateName(@PathVariable int empid, @RequestBody String name) {
        Physician updatedPhysician = physicianService.updateName(empid, name);
        return updatedPhysician != null ? ResponseEntity.ok(updatedPhysician) : ResponseEntity.notFound().build();
    }
}
