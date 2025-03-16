package com.hms.user_service.model;


import lombok.*;

import javax.persistence.*;

@Entity(name = "affiliated_with")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
}
