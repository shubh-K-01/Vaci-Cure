package com.demeatrix.VaciCure.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class ApiException {

    private String error;
    private int statusCode;
    private LocalDateTime timestamp;

    public ApiException(String error, int statusCode, LocalDateTime timestamp) {
        this.timestamp = LocalDateTime.now();
        this.error = error;
        this.statusCode = statusCode;
    }

}
