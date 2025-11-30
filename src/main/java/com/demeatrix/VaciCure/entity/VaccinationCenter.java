package com.demeatrix.VaciCure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * A physical vaccination center (hospital, clinic, PHC, camp site).
 * Replaces the loosely-typed facilityId/facilityName strings on Appointment.
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "vaccination_centers",
        indexes = {
                @Index(name = "idx_center_org", columnList = "organization_id"),
                @Index(name = "idx_center_active", columnList = "isActive")
        }
)
public class VaccinationCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long centerId;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(length = 100)
    private String centerType;  // HOSPITAL, CLINIC, PHC, CAMP

    @Column(nullable = false, length = 500)
    private String address;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(length = 20)
    private String postalCode;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(length = 20)
    private String contactPhone;

    @Column(length = 255)
    private String contactEmail;

    /** Maximum patients this center can handle per day. */
    @Column(nullable = false)
    private Integer dailyCapacity = 100;

    @NotNull
    @Column(nullable = false)
    private Boolean isActive = true;

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
