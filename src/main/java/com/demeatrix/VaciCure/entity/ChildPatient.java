package com.demeatrix.VaciCure.entity;

import com.demeatrix.VaciCure.entity.gender.GenderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Table(
        uniqueConstraints = {
                // @UniqueConstraint(name = "unique_parent_contact", columnNames = {"phoneNumber"}),
                @UniqueConstraint(name = "unique_child_patient", columnNames = {"childPatientName"})
        },
        indexes = {
                @Index(name = "idx_child_patient_name", columnList = "childPatientName"),
                @Index(name = "idx_child_patient_birth_date", columnList = "childBirthDate")
        }
)
public class ChildPatient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long childPatientId;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String childPatientName;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @NotNull
    @Past
    @Column(nullable = false)
    private LocalDate childBirthDate;

    @NotBlank
    @Column(nullable = false)
    private String parentName;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number")
    @Column(unique = true,nullable = false, length = 20)
    private String parentPhoneNumber;

    @OneToMany(mappedBy = "childPatient", cascade = {CascadeType.REMOVE})
    private List<Appointment> appointments = new ArrayList<>();

}
