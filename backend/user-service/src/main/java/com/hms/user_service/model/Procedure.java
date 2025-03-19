package com.hms.user_service.model;

import javax.persistence.*;

@Entity(name = "procedures")
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "cost", nullable = false)
    private Double cost;

    // Default constructor for JPA
    public Procedure() {
    }

    // Parameterized constructor
    public Procedure(int id, String name, Double cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
