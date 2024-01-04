package ru.jungle.creator.tables.handler;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.jungle.creator.tables.dto.response.ErrorSqlResponse;
import ru.jungle.creator.tables.dto.response.ErrorValidationResponse;
import ru.jungle.creator.tables.exception.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationHandler {

    private static final String DELIMITER = ".";

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorValidationResponse validationError(final MethodArgumentNotValidException e) {
        final ErrorValidationResponse errorValidationResponse = new ErrorValidationResponse(HttpStatus.BAD_REQUEST);
        errorValidationResponse.setMessages(
                e.getAllErrors()
                        .stream()
                        .map(m -> {
                            String field = ((FieldError) m).getField();
                            if (field.contains(DELIMITER)) {
                                String resultField = field.substring(field.indexOf(DELIMITER) + 1);
                                return new ErrorValidationResponse.ParameterErrorMessage(resultField, m.getDefaultMessage());
                            }
                            return new ErrorValidationResponse.ParameterErrorMessage(field, m.getDefaultMessage());
                        })
                        .collect(Collectors.toList()));
        return errorValidationResponse;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorValidationResponse validationParameterError(final ValidationException e) {
        final ErrorValidationResponse errorValidationResponse = new ErrorValidationResponse(HttpStatus.BAD_REQUEST);
        List<ErrorValidationResponse.ParameterErrorMessage> messages= List.of(new ErrorValidationResponse.ParameterErrorMessage(e.getParameterName(), e.getReason()));
        errorValidationResponse.setMessages(messages);
        return errorValidationResponse;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorSqlResponse sqlError (final BadSqlGrammarException e) {
        final ErrorSqlResponse errorSqlResponse = new ErrorSqlResponse(HttpStatus.BAD_REQUEST);
        errorSqlResponse.setMessage(new ErrorSqlResponse.SqlErrorMessage(e.getSql(), e.getSQLException().getMessage()));
        return errorSqlResponse;
    }
}
