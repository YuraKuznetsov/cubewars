package org.geekhub.yurii.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicatedLikeException extends RuntimeException {

    public DuplicatedLikeException(String message) {
        super(message);
    }
}