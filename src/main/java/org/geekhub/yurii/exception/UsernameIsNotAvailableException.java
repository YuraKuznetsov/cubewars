package org.geekhub.yurii.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UsernameIsNotAvailableException extends AuthenticationException {

    public UsernameIsNotAvailableException(String massage) {
        super(massage);
    }
}
