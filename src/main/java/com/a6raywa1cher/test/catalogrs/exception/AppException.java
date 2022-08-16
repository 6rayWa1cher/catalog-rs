package com.a6raywa1cher.test.catalogrs.exception;

public class AppException extends RuntimeException {
    private final int statusCode;

    public AppException(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
