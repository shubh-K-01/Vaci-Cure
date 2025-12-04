package com.demeatrix.VaciCure.enums;

/**
 * Lifecycle status of a single dose slot in a patient's vaccination schedule.
 */
public enum VaccinationStatus {
    /** Dose has been scheduled but not yet administered. */
    SCHEDULED,
    /** Dose was successfully administered. */
    COMPLETED,
    /** Dose date has passed without administration. */
    OVERDUE,
    /** Clinician deliberately skipped this dose (e.g., contraindication). */
    SKIPPED,
    /** Record was voided due to data entry error. */
    VOIDED
}
