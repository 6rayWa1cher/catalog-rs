package com.a6raywa1cher.test.catalogrs.exception.handler;

import com.a6raywa1cher.test.catalogrs.exception.EntityNotFoundAppException;
import com.a6raywa1cher.test.catalogrs.exception.FileStorageOperationAppException;
import com.a6raywa1cher.test.catalogrs.exception.UniqueConstraintViolationAppException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.a6raywa1cher.test.catalogrs.utils.CollectionUtils.mapToString;
import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(EntityNotFoundAppException.class)
    @ApiResponse(responseCode = "404", description = "not found",
            content = @Content(mediaType = "application/json", examples = @ExampleObject("""
                    {
                      "status": "NOT_FOUND",
                      "timestamp": "2022-08-18T19:55:39.1411325+03:00",
                      "message": "Entity ProductCategory{id=2} isn't found",
                      "debugMessage": null,
                      "subErrors": null
                    }
                    """),
                    schema = @Schema(implementation = ApiError.class)
            ))
    public ResponseEntity<ApiError> handleEntityNotFound(
            EntityNotFoundAppException exception
    ) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage("Entity %s{id=%d} isn't found"
                .formatted(exception.getEntityClass().getSimpleName(), exception.getId()));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(FileStorageOperationAppException.class)
    public ResponseEntity<ApiError> handleFileStorageOperation(
            FileStorageOperationAppException exception
    ) {
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR);
        apiError.setMessage("File storage processing failed");
        String operationName = exception.getOperationName();
        String objectName = exception.getObjectName();
        Throwable cause = exception.getCause();
        apiError.setDebugMessage("Operation: %s, object: %s\nCause: %s".formatted(operationName, objectName, cause.toString()));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(UniqueConstraintViolationAppException.class)
    @ApiResponse(responseCode = "409", description = "conflict: unique constraint violation",
            content = @Content(mediaType = "application/json", examples = @ExampleObject("""
                    {
                      "status": "CONFLICT",
                      "timestamp": "2022-08-18T20:16:19.8937104+03:00",
                      "message": "Unique constraint violation: {title = string}",
                      "debugMessage": null,
                      "subErrors": null
                    }
                    """),
                    schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<ApiError> handleUniqueConstraintViolation(UniqueConstraintViolationAppException exception) {
        ApiError apiError = new ApiError(CONFLICT);
        Map<String, Object> violations = exception.getViolations();
        apiError.setMessage("Unique constraint violation: {%s}".formatted(mapToString(violations, ",")));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponse(responseCode = "400", description = "validation failure",
            content = @Content(mediaType = "application/json", examples = @ExampleObject("""
                    {
                      "status": "BAD_REQUEST",
                      "timestamp": "2022-08-18T20:13:37.7563971+03:00",
                      "message": "Validation failed for fields [title]",
                      "debugMessage": "...",
                      "subErrors": [
                        {
                          "object": "productCategoryRequestDto",
                          "field": "title",
                          "rejectedValue": null,
                          "message": "must not be blank"
                        }
                      ]
                    }"""),
                    schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        String failedFields = exception.getFieldErrors().stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(","));
        apiError.setMessage("Validation failed for fields [%s]".formatted(failedFields));
        apiError.setDebugMessage(exception.getMessage());
        apiError.setSubErrors(fieldErrorsToSubErrors(exception.getFieldErrors()));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ApiResponse(responseCode = "413", description = "file content or json object is too large",
            content = @Content(mediaType = "application/json", examples = @ExampleObject("""
                    {
                      "status": "PAYLOAD_TOO_LARGE",
                      "timestamp": "2022-08-18T20:42:22.1715387+03:00",
                      "message": "Configured maximum size for a payload exceeded (518241333 > 4194304)",
                      "debugMessage": null,
                      "subErrors": null
                    }
                    """),
                    schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<ApiError> handleSizeLimitExceeded(MaxUploadSizeExceededException exception) {
        ApiError apiError = new ApiError(PAYLOAD_TOO_LARGE);
        Throwable rootCause = exception.getRootCause();
        if (rootCause instanceof SizeLimitExceededException rootException) {
            long permittedSize = rootException.getPermittedSize();
            long actualSize = rootException.getActualSize();
            apiError.setMessage("Configured maximum size for a payload exceeded (%d > %d)".formatted(actualSize, permittedSize));
        } else {
            apiError.setMessage("Configured maximum size for a payload exceeded");
        }
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        ApiError apiError = new ApiError(UNPROCESSABLE_ENTITY);
        apiError.setMessage("Payload is not readable");
        apiError.setDebugMessage(exception.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    @ApiResponse(responseCode = "500", description = "unknown error",
            content = @Content(mediaType = "application/json", examples = @ExampleObject("""
                    {
                      "status": "INTERNAL_SERVER_ERROR",
                      "timestamp": "2022-08-18T20:37:55.3449157+03:00",
                      "message": "Internal server error",
                      "debugMessage": null,
                      "subErrors": null
                    }
                    """),
                    schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<ApiError> handleUncaughtException(Exception exception) {
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR);
        apiError.setMessage("Internal server error");
        log.error("Exception caught", exception);
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private List<ApiSubError> fieldErrorsToSubErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> (ApiSubError) new ApiValidationSubError(
                        fieldError.getObjectName(),
                        fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()
                ))
                .toList();
    }
}