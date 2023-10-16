package ru.jungle.creator.tables.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class ErrorValidationResponse extends ErrorResponse {

    // List of message templates for client-side rendering
    private List<ParameterErrorMessage> messages;

    public ErrorValidationResponse(HttpStatus httpStatus) {
        super(httpStatus);
    }

    @Getter
    @AllArgsConstructor
    public static class ParameterErrorMessage {
        private String parameterName;
        private String error;
    }
}
