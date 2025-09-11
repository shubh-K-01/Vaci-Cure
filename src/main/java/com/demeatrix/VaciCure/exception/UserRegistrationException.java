package com.demeatrix.VaciCure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserRegistrationException extends UserServiceException {
    public UserRegistrationException(String message) {
        super(message);
    }
}
