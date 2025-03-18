package com.hms.user_service.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private int appointmentId;

    @Column(name = "physician_id", nullable = false)
    private int physicianId;

    @Column(name = "nurse_id", nullable = false)
    private int nurseId;

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "examination_room_id", nullable = false)
    private int examinationRoomId;

    // Getters and Setters
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(int physicianId) {
        this.physicianId = physicianId;
    }

    public int getNurseId() {
        return nurseId;
    }

    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getExaminationRoomId() {
        return examinationRoomId;
    }

    public void setExaminationRoomId(int examinationRoomId) {
        this.examinationRoomId = examinationRoomId;
    }
}

