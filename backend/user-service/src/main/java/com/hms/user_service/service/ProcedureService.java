package com.hms.user_service.service;

import com.hms.user_service.model.Procedure;
import com.hms.user_service.repo.ProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProcedureService {

    private final ProcedureRepository procedureRepository;

    @Autowired
    public ProcedureService(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    // Create a new procedure
    public Procedure createProcedure(Procedure procedure) {
        return procedureRepository.save(procedure);
    }

    // Get all procedures
    public Iterable<Procedure> getAllProcedures() {
        return procedureRepository.findAll();
    }

    // Get procedure by id
    public Optional<Procedure> getProcedureById(int id) {
        return procedureRepository.findById(id);
    }

    // Get procedure by name
    public Optional<Procedure> getProcedureByName(String name) {
        return procedureRepository.findByName(name);
    }

    // Update procedure cost by id
    public Optional<Procedure> updateProcedureCost(int id, Double cost) {
        Optional<Procedure> procedure = procedureRepository.findById(id);
        if (procedure.isPresent()) {
            Procedure updatedProcedure = procedure.get();
            updatedProcedure.setCost(cost);
            return Optional.of(procedureRepository.save(updatedProcedure));
        }
        return Optional.empty();
    }

    // Update procedure name by id
    public Optional<Procedure> updateProcedureName(int id, String name) {
        Optional<Procedure> procedure = procedureRepository.findById(id);
        if (procedure.isPresent()) {
            Procedure updatedProcedure = procedure.get();
            updatedProcedure.setName(name);
            return Optional.of(procedureRepository.save(updatedProcedure));
        }
        return Optional.empty();
    }
}
