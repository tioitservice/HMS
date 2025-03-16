package com.hms.user_service.model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Room {
    @Id
    private int roomNo;
    private boolean booked;
}