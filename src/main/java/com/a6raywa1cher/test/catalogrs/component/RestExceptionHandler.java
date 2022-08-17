package com.a6raywa1cher.test.catalogrs.component;

import com.a6raywa1cher.test.catalogrs.exception.EntityNotFoundAppException;
import com.a6raywa1cher.test.catalogrs.exception.FileStorageOperationAppException;
import com.a6raywa1cher.test.catalogrs.exception.UniqueConstraintViolationAppException;
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
public class RestExceptionHandler {
    @ExceptionHandler(EntityNotFoundAppException.class)
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
    public ResponseEntity<ApiError> handleUniqueConstraintViolation(UniqueConstraintViolationAppException exception) {
        ApiError apiError = new ApiError(CONFLICT);
        Map<String, Object> violations = exception.getViolations();
        apiError.setMessage("Unique constraint violation: {%s}".formatted(mapToString(violations, ",")));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
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
    public ResponseEntity<ApiError> handleUncaughtException(Exception exception) {
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR);
        apiError.setMessage("Internal server error: %s".formatted(exception.getClass().getSimpleName()));
        apiError.setDebugMessage(exception.getMessage());
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