package com.demeatrix.VaciCure.entity;

import com.demeatrix.VaciCure.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Appointment {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, name = "appointment_at")
    private LocalDateTime appointment_at;

    @Column(length = 500)
    private String reason;

    @ManyToOne
    @JoinColumn(name = "child_patient_id", nullable = false)
    private ChildPatient childPatient;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Column(name = "facility_id", nullable = false)
    private Long facilityId;

    @Column(nullable = false)
    private String facilityName;

}
