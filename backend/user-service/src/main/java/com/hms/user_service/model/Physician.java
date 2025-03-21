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

    private String ssn;

    public Physician() {
    }

    public Physician(int employeeId, String name, String position, int deptId, int trainId, String ssn) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.deptId = deptId;
        this.trainId = trainId;
        this.ssn = ssn;
    }

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

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}
