package com.demeatrix.VaciCure.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Standardized gender type enum used across all patient/worker entities.
 * Replaces the old entity.gender.GenderType.
 */
public enum GenderType {
    MALE,
    FEMALE,
    OTHER;

    @JsonCreator
    public static GenderType fromValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Gender value cannot be null or blank");
        }
        return GenderType.valueOf(value.toUpperCase());
    }
}
