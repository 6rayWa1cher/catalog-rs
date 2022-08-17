package com.a6raywa1cher.test.catalogrs.component;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ApiError {
    private final HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime timestamp = ZonedDateTime.now();

    private String message;

    private String debugMessage;

    private List<ApiSubError> subErrors;
}
