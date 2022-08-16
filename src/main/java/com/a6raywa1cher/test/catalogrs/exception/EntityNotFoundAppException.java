package com.a6raywa1cher.test.catalogrs.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class EntityNotFoundAppException extends AppException {
    private final long id;
    private final Class<?> entityClass;

    public EntityNotFoundAppException(long id, Class<?> entityClass) {
        super(NOT_FOUND.value());
        this.id = id;
        this.entityClass = entityClass;
    }

    public long getId() {
        return id;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
}
