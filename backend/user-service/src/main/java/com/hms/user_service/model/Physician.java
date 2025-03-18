package com.hms.user_service.model;

import javax.persistence.*;

@Entity(name = "physicians")
public class Physician {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employeeId")
    private int employeeId;

    private String name;

    private String position;

    private int deptId;

    private int trainId;

    // Default constructor for JPA
    public Physician() {
    }

    // Parameterized constructor
    public Physician(int employeeId, String name, String position, int deptId, int trainId) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.deptId = deptId;
        this.trainId = trainId;
    }

    // Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }
}
