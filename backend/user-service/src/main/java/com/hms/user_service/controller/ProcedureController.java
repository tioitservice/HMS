package com.hms.user_service.controller;

import com.hms.user_service.model.Procedure;
import com.hms.user_service.service.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class ProcedureController {

    private final ProcedureService procedureService;

    @Autowired
    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    // POST: Create a new Procedure
    @PostMapping("/procedure")
    public ResponseEntity<Procedure> createProcedure(@RequestBody Procedure procedure) {
        Procedure createdProcedure = procedureService.createProcedure(procedure);
        return ResponseEntity.ok(createdProcedure);
    }

    // GET: Get all Procedures
    @GetMapping("/procedure")
    public ResponseEntity<Iterable<Procedure>> getAllProcedures() {
        Iterable<Procedure> procedures = procedureService.getAllProcedures();
        return ResponseEntity.ok(procedures);
    }

    // GET: Get Procedure cost by id
    @GetMapping("/procedure/cost/{id}")
    public ResponseEntity<Double> getProcedureCostById(@PathVariable int id) {
        Optional<Procedure> procedure = procedureService.getProcedureById(id);
        return procedure.map(p -> ResponseEntity.ok(p.getCost())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET: Get Procedure cost by name
    @GetMapping("/procedure/cost/name/{name}")
    public ResponseEntity<Double> getProcedureCostByName(@PathVariable String name) {
        Optional<Procedure> procedure = procedureService.getProcedureByName(name);
        return procedure.map(p -> ResponseEntity.ok(p.getCost())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT: Update Procedure cost by id
    @PutMapping("/procedure/cost/{id}")
    public ResponseEntity<Procedure> updateProcedureCost(@PathVariable int id, @RequestBody Double cost) {
        Optional<Procedure> updatedProcedure = procedureService.updateProcedureCost(id, cost);
        return updatedProcedure.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT: Update Procedure name by id
    @PutMapping("/procedure/name/{id}")
    public ResponseEntity<Procedure> updateProcedureName(@PathVariable int id, @RequestBody String name) {
        Optional<Procedure> updatedProcedure = procedureService.updateProcedureName(id, name);
        return updatedProcedure.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
