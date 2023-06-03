package org.geekhub.yurii.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
public class CommentLimitReachedException extends RuntimeException {

    public CommentLimitReachedException(String massage) {
        super(massage);
    }
}
