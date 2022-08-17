package com.a6raywa1cher.test.catalogrs.exception;

import java.util.Map;

public class UniqueConstraintViolationAppException extends AppException {
    private final Map<String, Object> violations;

    public UniqueConstraintViolationAppException(Map<String, Object> violations) {
        this.violations = violations;
    }

    public Map<String, Object> getViolations() {
        return violations;
    }
}
