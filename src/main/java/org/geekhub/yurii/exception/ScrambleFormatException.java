package org.geekhub.yurii.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ScrambleFormatException extends RuntimeException {

    public ScrambleFormatException(String massage) {
        super(massage);
    }
}
