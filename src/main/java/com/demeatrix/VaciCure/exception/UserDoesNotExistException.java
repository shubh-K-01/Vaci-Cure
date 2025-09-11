package com.demeatrix.VaciCure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserDoesNotExistException extends UserServiceException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}