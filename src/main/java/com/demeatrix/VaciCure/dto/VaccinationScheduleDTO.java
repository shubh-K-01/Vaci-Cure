package com.demeatrix.VaciCure.dto;

import com.demeatrix.VaciCure.enums.VaccinationStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationScheduleDTO {

    private Long scheduleId;

    private Long patientId;

    private Long vaccineId;
    private String vaccineName;

    private Integer doseNumber;

    private LocalDate scheduledDate;

    private VaccinationStatus status;

    private LocalDateTime reminderSentAt;

    /** Populated when status is COMPLETED. */
    private VaccinationRecordDTO vaccinationRecord;
}
