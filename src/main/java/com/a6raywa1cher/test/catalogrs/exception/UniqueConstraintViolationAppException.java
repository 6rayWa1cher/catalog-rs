package com.a6raywa1cher.test.catalogrs.exception;

import java.util.Map;

import static org.springframework.http.HttpStatus.CONFLICT;

public class UniqueConstraintViolationAppException extends AppException {
    private final Map<String, Object> violations;

    public UniqueConstraintViolationAppException(Map<String, Object> violations) {
        super(CONFLICT.value());
        this.violations = violations;
    }

    public Map<String, Object> getViolations() {
        return violations;
    }
}
