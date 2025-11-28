package com.demeatrix.VaciCure.dto;

import com.demeatrix.VaciCure.entity.Appointment;
import com.demeatrix.VaciCure.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO implements Serializable {

    private Long doctorId;

    private String fullName;

    private String specialization;

    private String email;

    private String licenseNumber;

    private String password;

    private Set<Department> departments = new HashSet<>();
}
