package com.demeatrix.VaciCure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a healthcare organization (hospital chain, government health department, NGO).
 * Top-level tenant in multi-org deployments.
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizationId;

    @NotBlank
    @Column(nullable = false, unique = true, length = 200)
    private String name;

    @Column(length = 50)
    private String type;  // HOSPITAL_CHAIN, GOVERNMENT, NGO, CORPORATE

    @Column(length = 20)
    private String contactPhone;

    @Column(length = 255)
    private String contactEmail;

    @Column(length = 500)
    private String address;

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
