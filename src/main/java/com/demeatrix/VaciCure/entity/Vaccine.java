package com.demeatrix.VaciCure.entity;

import com.demeatrix.VaciCure.enums.AdministrationRoute;
import com.demeatrix.VaciCure.enums.VaccineType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Master vaccine catalog entry. Represents a vaccine product (e.g., "BCG" by "Serum Institute").
 * This is NOT a batch — batches reference this entity.
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "vaccines",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_vaccine_name_mfr", columnNames = {"name", "manufacturer"})
        }
)
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vaccineId;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;  // e.g., "BCG", "OPV", "Covishield", "Pentavalent"

    @NotBlank
    @Column(nullable = false, length = 100)
    private String manufacturer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private VaccineType vaccineType;

    /** JSON array of target diseases, e.g., ["Tuberculosis"] or ["Diphtheria","Tetanus","Pertussis"] */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String targetDiseases;

    /** Minimum storage temperature in °C (e.g., 2.0) */
    @NotNull
    @Column(nullable = false, precision = 4, scale = 1)
    private BigDecimal storageTempMin;

    /** Maximum storage temperature in °C (e.g., 8.0) */
    @NotNull
    @Column(nullable = false, precision = 4, scale = 1)
    private BigDecimal storageTempMax;

    /** Total doses required for full immunization */
    @Column(nullable = false)
    private Integer dosesRequired = 1;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private AdministrationRoute route;

    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "vaccine", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("doseNumber ASC")
    @Builder.Default
    private List<DoseScheduleTemplate> doseSchedules = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
