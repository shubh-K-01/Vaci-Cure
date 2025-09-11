package com.demeatrix.VaciCure.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AppointmentStatus {
    BOOKED,
    CHECKED_IN,
    COMPLETED,
    CANCELLED,
    NO_SHOW;

    @JsonCreator
    public static AppointmentStatus fromValue(String value) {
        return AppointmentStatus.valueOf(value.toUpperCase());
    }

}
