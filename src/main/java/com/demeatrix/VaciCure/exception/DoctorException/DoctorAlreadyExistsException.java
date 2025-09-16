package com.demeatrix.VaciCure.exception.DoctorException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DoctorAlreadyExistsException extends RuntimeException{
    public DoctorAlreadyExistsException(String message) {
        super(message);
    }
}