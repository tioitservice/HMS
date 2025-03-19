package com.hms.user_service.model;

import javax.persistence.*;

@Entity(name = "affiliated_with")
public class AffiliatedWith {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "physician_id", nullable = false)
    private String physicianId;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary;  // Indicates if this affiliation is the primary one for the physician

    // Default constructor for JPA
    public AffiliatedWith() {
    }

    // Parameterized constructor
    public AffiliatedWith(int id, String physicianId, Long departmentId, boolean isPrimary) {
        this.id = id;
        this.physicianId = physicianId;
        this.departmentId = departmentId;
        this.isPrimary = isPrimary;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(String physicianId) {
        this.physicianId = physicianId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }
}
