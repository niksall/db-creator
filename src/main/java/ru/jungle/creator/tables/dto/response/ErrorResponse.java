package ru.jungle.creator.tables.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    // Event time
    private ZonedDateTime timestamp;

    // In case you need to return the extended status of the message (successful response or error)
    private int status;

    // In case you need to return the extended status of the message (successful response or error)
    private String error;

    public ErrorResponse(final HttpStatus httpStatus) {
        this.timestamp = ZonedDateTime.now();
        this.status = httpStatus.value();
        this.error = httpStatus.name();
    }
}
