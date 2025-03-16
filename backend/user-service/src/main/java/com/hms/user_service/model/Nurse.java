package com.hms.user_service.model;


import javax.persistence.*;

@Entity
@Table(name = "nurse")
public class Nurse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empid")
    private int empId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "is_registered", nullable = false)
    private boolean isRegistered;

    // Getters and Setters
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
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

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }
}
