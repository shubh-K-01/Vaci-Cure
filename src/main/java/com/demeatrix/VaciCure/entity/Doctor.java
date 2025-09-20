package com.demeatrix.VaciCure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long doctorId;

    @Column(nullable = false)
    private String fullName;

    @Column(length = 100)
    private String specialization;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private String password;

    @ManyToMany(mappedBy = "doctors")
    private Set<Department> departments = new HashSet<>();

}
