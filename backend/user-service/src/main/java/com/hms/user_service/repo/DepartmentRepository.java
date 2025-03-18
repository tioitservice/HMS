package com.hms.user_service.repo;

import com.hms.user_service.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Optional<Department> findByDeptId(int deptId);

    Optional<Department> findByDeptName(String deptName);

//    Optional<Department> findByHeadId(String headId);

    Optional<Department> findByHeadName(String headName);
}
