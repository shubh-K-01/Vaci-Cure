package com.demeatrix.VaciCure.exception.UserException;

import com.demeatrix.VaciCure.exception.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserRegistrationException extends UserServiceException {
    public UserRegistrationException(String message) {
        super(message);
    }
}
