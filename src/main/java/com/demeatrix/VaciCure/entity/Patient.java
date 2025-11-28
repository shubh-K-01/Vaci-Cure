package com.demeatrix.VaciCure.entity;

import com.demeatrix.VaciCure.enums.GenderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Universal patient entity supporting all ages (adults + children).
 * Children are linked to a guardian (parent) via the guardian_user_id FK.
 *
 * Replaces the old ChildPatient entity which only supported children
 * and had a uniqueness constraint on patient name (a critical data integrity bug).
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "patients",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_patient_national_id", columnNames = {"nationalIdHash"})
        },
        indexes = {
                @Index(name = "idx_patient_phone", columnList = "phoneNumber"),
                @Index(name = "idx_patient_dob", columnList = "dateOfBirth"),
                @Index(name = "idx_patient_guardian", columnList = "guardian_user_id"),
                @Index(name = "idx_patient_national_id", columnList = "nationalIdHash")
        }
)
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    /** Public-facing ID for external references (URLs, QR codes). Never expose DB primary key. */
    @Column(nullable = false, unique = true, updatable = false, length = 26)
    private String publicId;

    /** SHA-256 hash of national ID (Aadhaar, SSN). Never store raw. */
    @Column(length = 64)
    private String nationalIdHash;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String fullName;

    @NotNull
    @Past
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private GenderType gender;

    @Column(length = 5)
    private String bloodGroup;

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number")
    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 255)
    private String email;

    @Column(length = 255)
    private String addressLine1;

    @Column(length = 255)
    private String addressLine2;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(length = 20)
    private String postalCode;

    /** JSON array of known allergies. Stored as text for flexibility. */
    @Column(columnDefinition = "TEXT")
    private String allergies;

    /** JSON array of comorbidities (diabetes, immunocompromised, etc.) */
    @Column(columnDefinition = "TEXT")
    private String comorbidities;

    /** True if patient is under 18 — requires guardian linkage. */
    @Column(nullable = false)
    private Boolean isMinor = false;

    /** The User account of the parent/guardian (for minors). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guardian_user_id")
    private User guardianUser;

    @Column(nullable = false)
    private Boolean consentGiven = false;

    private LocalDateTime consentGivenAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column
    private Long createdBy;

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
