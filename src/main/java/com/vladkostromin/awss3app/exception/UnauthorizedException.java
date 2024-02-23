package com.vladkostromin.awss3app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends ApiException {

    public UnauthorizedException(String message) {
        super(message, "VLADKOSTROV_UNAUTHORIZED");
    }
}
