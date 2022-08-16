package com.a6raywa1cher.test.catalogrs.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FileStorageOperationException extends AppException {
    private final String operationName;
    private final String objectName;

    public FileStorageOperationException(String operationName, Exception cause) {
        this(operationName, null, cause);
    }

    public FileStorageOperationException(String operationName, String objectName, Exception cause) {
        super(INTERNAL_SERVER_ERROR.value(), cause);
        this.operationName = operationName;
        this.objectName = objectName;
    }

    public String getOperationName() {
        return operationName;
    }

    public String getObjectName() {
        return objectName;
    }
}
