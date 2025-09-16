package com.demeatrix.VaciCure.exception.UserException;

import com.demeatrix.VaciCure.exception.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserDoesNotExistException extends UserServiceException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}