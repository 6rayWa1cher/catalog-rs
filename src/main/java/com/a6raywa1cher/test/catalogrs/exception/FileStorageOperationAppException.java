package com.a6raywa1cher.test.catalogrs.exception;

public class FileStorageOperationAppException extends AppException {
    private final String operationName;
    private final String objectName;

    public FileStorageOperationAppException(String operationName, Exception cause) {
        this(operationName, null, cause);
    }

    public FileStorageOperationAppException(String operationName, String objectName, Exception cause) {
        super(cause);
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
