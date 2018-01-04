package com.kabasakalis.springifyapi.errors;


import com.kabasakalis.springifyapi.models.BaseEntity;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class EntityNotFoundException extends BaseException {

    private Class<? extends BaseEntity> entityClass;
    private long entity_id;

    public EntityNotFoundException(
            Class<? extends  BaseEntity> _class,
            long entity_id,
            HttpStatus code,
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace
    ) {
        super(code, message, cause, enableSuppression, writableStackTrace);
        this.entity_id = entity_id;
        this.customMessage = String.format("%s with %d could not be found.", _class.getSimpleName(), entity_id);
    }

    public Class<? extends BaseEntity> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<? extends BaseEntity> entityClass) {
        this.entityClass = entityClass;
    }

    public long getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(long entity_id) {
        this.entity_id = entity_id;
    }

}
