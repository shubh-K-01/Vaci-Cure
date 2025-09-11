package com.demeatrix.VaciCure.entity.gender;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GenderType {
    Male,
    Female,
    Other;

    @JsonCreator
    public static GenderType fromValue(String value) {
        return GenderType.valueOf(value.toUpperCase());
    }
}
