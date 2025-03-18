package com.hms.user_service.controller;

import com.hms.user_service.model.Department;
import com.hms.user_service.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/user")  // Updated the base URL
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Create a new Department
    @PostMapping("/department")
    public Department createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentService.createDepartment(department);
        return createdDepartment;
    }

    // Get all Departments
    @GetMapping("/department")
    public ResponseEntity<Iterable<Department>> getAllDepartments() {
        Iterable<Department> departments = departmentService.getAllDepartment();
        return ResponseEntity.ok(departments);
    }

    // Get Department by deptId
    @GetMapping("/department/{deptId}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int deptId) {
        Optional<Department> department = departmentService.getDepartmentById(deptId);
        return department.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    // Get Department head certification by deptId
    @GetMapping("/department/headcertification/{deptId}")
    public ResponseEntity<String> getDepartmentHeadCertification(@PathVariable int deptId) {
        Optional<Department> department = departmentService.getDepartmentById(deptId);
        if (department.isPresent()) {
            return ResponseEntity.ok(department.get().getHeadCertification());
        }
        return ResponseEntity.notFound().build();
    }

    // Get Department by Head Name
    @GetMapping("/department/byhead/{headName}")
    public ResponseEntity<Department> getDepartmentByHeadName(@PathVariable String headName) {
        Optional<Department> department = departmentService.getDepartmentByHeadName(headName);
        return department.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Check if a Physician is assigned to a Department
//    @GetMapping("/department/check/{physicianId}")
//    public ResponseEntity<Boolean> checkPhysicianInDepartment(@PathVariable String physicianId) {
//        Optional<Department> department = departmentService.getDepartmentByHeadId(physicianId);
//        return department.isPresent() ? ResponseEntity.ok(true) : ResponseEntity.ok(false);
//    }

    // Update Department head ID
    @PutMapping("/department/update/headid/{deptId}")
    public ResponseEntity<Department> updateDepartmentHeadId(@PathVariable int deptId, @RequestBody String headId) {
        Department updatedDepartment = departmentService.updateDepartmentHeadId(deptId, headId);
        return updatedDepartment != null ? ResponseEntity.ok(updatedDepartment) : ResponseEntity.notFound().build();
    }

    // Update Department name
    @PutMapping("/department/update/deptname/{deptId}/{deptName}")
    public ResponseEntity<Department> updateDepartmentName(@PathVariable int deptId, @PathVariable String deptName) {
        Department updatedDepartment = departmentService.updateDepartmentName(deptId, deptName);
        return updatedDepartment != null ? ResponseEntity.ok(updatedDepartment) : ResponseEntity.notFound().build();
    }
}
