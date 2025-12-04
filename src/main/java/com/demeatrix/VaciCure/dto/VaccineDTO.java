package com.demeatrix.VaciCure.dto;

import com.demeatrix.VaciCure.enums.AdministrationRoute;
import com.demeatrix.VaciCure.enums.VaccineType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VaccineDTO {

    private Long vaccineId;

    @NotBlank(message = "Vaccine name is required")
    private String name;

    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    @NotNull(message = "Vaccine type is required")
    private VaccineType vaccineType;

    /** Comma-separated or JSON array of target diseases. */
    private String targetDiseases;

    @NotNull
    private BigDecimal storageTempMin;

    @NotNull
    private BigDecimal storageTempMax;

    private Integer dosesRequired;

    private AdministrationRoute route;

    private Boolean isActive;

    /** Embedded dose schedule templates. Populated on GET, optional on POST. */
    private List<DoseScheduleTemplateDTO> doseSchedules;
}
