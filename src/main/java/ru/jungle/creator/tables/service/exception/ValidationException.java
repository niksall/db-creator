package ru.jungle.creator.tables.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class ValidationException extends ResponseStatusException {

    private final String parameterName;

    public ValidationException(final String parameterName, final String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
        this.parameterName = parameterName;
    }
}