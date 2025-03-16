package com.hms.user_service.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "physicians")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Physician {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employeeId")
    private int employeeId;

    private String name;

    private String position;
}
