package com.hms.user_service.service;


import com.hms.user_service.model.Department;
import com.hms.user_service.repo.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Optional<Department> getDepartmentById(int deptId) {
        return departmentRepository.findByDeptId(deptId);
    }

    public Optional<Department> getDepartmentByName(String deptName) {
        return departmentRepository.findByDeptName(deptName);
    }

    public Optional<Department> getDepartmentByHeadId(String headId) {
        return departmentRepository.findByHeadId(headId);
    }

    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }
    public Optional<Department> getDepartmentByHeadName(String headName) {
        return departmentRepository.findByHeadName(headName);
    }

    public Department updateDepartmentHeadId(int deptId, String headId) {
        Optional<Department> department = departmentRepository.findByDeptId(deptId);
        if (department.isPresent()) {
            department.get().setHeadId(headId);
            return departmentRepository.save(department.get());
        }
        return null;
    }

    public Department updateDepartmentName(int deptId, String deptName) {
        Optional<Department> department = departmentRepository.findByDeptId(deptId);
        if (department.isPresent()) {
            department.get().setDeptName(deptName);
            return departmentRepository.save(department.get());
        }
        return null;
    }
}
