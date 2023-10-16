package ru.jungle.creator.tables.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorSqlResponse extends ErrorResponse {

    // Message templates for client-side rendering
    private SqlErrorMessage message;

    public ErrorSqlResponse(HttpStatus httpStatus) {
        super(httpStatus);
    }

    @Getter
    @AllArgsConstructor
    public static class SqlErrorMessage {
        private String sqlRequest;
        private String errorMessage;
    }
}
