package com.hms.user_service.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "trained_in")
public class TrainedIn {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "physician_id", nullable = false)
    private int physicianId;

    @Column(name = "procedure_id", nullable = false)
    private int procedureId;

    @Column(name = "certification_expiry_date", nullable = false)
    private LocalDate certificationExpiryDate;

}
