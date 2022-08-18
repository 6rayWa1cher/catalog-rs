package com.a6raywa1cher.test.catalogrs.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ApiError {
    @Schema(type = "string", example = "NOT_FOUND")
    private final HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime timestamp = ZonedDateTime.now();

    private String message;

    private String debugMessage;

    private List<ApiSubError> subErrors;
}
