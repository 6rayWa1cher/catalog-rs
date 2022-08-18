package com.a6raywa1cher.test.catalogrs.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiValidationSubError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ApiValidationSubError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
