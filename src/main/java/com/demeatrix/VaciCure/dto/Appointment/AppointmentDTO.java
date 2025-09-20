package com.demeatrix.VaciCure.dto.Appointment;

import com.demeatrix.VaciCure.dto.ChildPatientDTO;
import com.demeatrix.VaciCure.entity.Appointment;
import com.demeatrix.VaciCure.entity.ChildPatient;
import com.demeatrix.VaciCure.entity.Doctor;
import com.demeatrix.VaciCure.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private Long Id;

    private LocalDateTime appointment_at;

    private String reason;

    private Long childPatientId;

    private Long doctorId;

    private AppointmentStatus status;

    private Long facilityId;

    private String facilityName;
}
