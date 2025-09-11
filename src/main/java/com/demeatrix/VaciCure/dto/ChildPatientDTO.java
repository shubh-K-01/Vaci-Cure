package com.demeatrix.VaciCure.dto;

import com.demeatrix.VaciCure.entity.Appointment;
import com.demeatrix.VaciCure.entity.gender.GenderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildPatientDTO {

    private Long childPatientId;

    private String childPatientName;

    private GenderType gender;

    private LocalDate childBirthDate;

    private String parentName;

    private String parentPhoneNumber;

    private List<Appointment> appointments = new ArrayList<>();

}
