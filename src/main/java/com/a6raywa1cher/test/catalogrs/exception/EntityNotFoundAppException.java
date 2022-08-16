package com.a6raywa1cher.test.catalogrs.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class EntityNotFoundAppException extends AppException {
    private long id;
    private Class<?> entityClass;

    public EntityNotFoundAppException(long id, Class<?> entityClass) {
        super(NOT_FOUND.value());
        this.id = id;
        this.entityClass = entityClass;
    }
}
