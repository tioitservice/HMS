package com.hms.user_service.model;

import javax.persistence.*;

@Entity(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "deptId")
    private int deptId;

    @Column(unique = true, nullable = false)
    private String deptName;

    private String headCertification;

    private String headName;

    // Default constructor for JPA
    public Department() {
    }

    // Parameterized constructor
    public Department(int deptId, String deptName, String headCertification, String headName) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.headCertification = headCertification;
        this.headName = headName;
    }

    // Getters and Setters
    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getHeadCertification() {
        return headCertification;
    }

    public void setHeadCertification(String headCertification) {
        this.headCertification = headCertification;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }
}
