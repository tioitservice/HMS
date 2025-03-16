package com.hms.user_service.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "procedures")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "cost", nullable = false)
    private Double cost;
}
