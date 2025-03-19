package com.hms.user_service.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "trained_in")
public class TrainedIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trainId;

    // Uncommented columns, assuming you need them
    // @Column(name = "physician_id", nullable = false)
    // private int physicianId;

    @Column(name = "training_name", nullable = false)
    private String trainingName;

    // Uncommented column for certification expiry date
    // @Column(name = "certification_expiry_date", nullable = false)
    // private LocalDate certificationExpiryDate;

    // Default constructor for JPA
    public TrainedIn() {
    }

    // Getters and Setters
    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    // Uncomment the following methods if you decide to use these fields
    /*
    public int getPhysicianId() {
        return physicianId;
    }

    public void setPhysicianId(int physicianId) {
        this.physicianId = physicianId;
    }
    */

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    /*
    public LocalDate getCertificationExpiryDate() {
        return certificationExpiryDate;
    }

    public void setCertificationExpiryDate(LocalDate certificationExpiryDate) {
        this.certificationExpiryDate = certificationExpiryDate;
    }
    */
}
