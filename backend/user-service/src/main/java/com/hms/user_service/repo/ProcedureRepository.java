package com.hms.user_service.repo;

import com.hms.user_service.model.Procedure;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface ProcedureRepository extends CrudRepository<Procedure, Integer> {

    // Find Procedure by name
    Optional<Procedure> findByName(String name);

    // Find Procedure by id
    Optional<Procedure> findById(int id);

}
