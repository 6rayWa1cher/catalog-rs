package com.a6raywa1cher.test.catalogrs.exception;

public class EntityNotFoundAppException extends AppException {
    private final long id;
    private final Class<?> entityClass;

    public EntityNotFoundAppException(long id, Class<?> entityClass) {
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
