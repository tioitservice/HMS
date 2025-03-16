package com.hms.user_service.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetails {
    private String name;
    private String phoneNumber;
    private String address;
}