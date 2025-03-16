package com.hms.user_service.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "departments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "deptId")
    private int deptId;

    @Column(unique = true, nullable = false)
    private String deptName;

    private String headId;

    private String headCertification;

    private String headName;
}
