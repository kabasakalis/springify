package com.kabasakalis.springifyapi.exceptions;


import com.kabasakalis.springifyapi.domain.BaseEntity;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class EntityNotFoundException extends BaseException {

    protected static final HttpStatus CODE = NOT_FOUND;
    private Class<? extends BaseEntity> entityClass;
    private long entity_id;

    public EntityNotFoundException(
            Class<? extends BaseEntity> _class,
            long entity_id,
            HttpStatus code,
            String message,
            Throwable cause
    ) {
        super(code, message, cause);
        this.entity_id = entity_id;
        this.customMessage = String.format("%s with id of %d could not be found.", _class.getSimpleName(), entity_id);
    }

    public EntityNotFoundException(Class<? extends BaseEntity> _class, long entity_id) {
        super(CODE);
        this.entity_id = entity_id;
        this.customMessage = String.format("%s with id of %d could not be found.", _class.getSimpleName(), entity_id);
    }

    public EntityNotFoundException(
            Class<? extends BaseEntity> _class

    ) {
        super(CODE);
        this.customMessage = String.format("%s could not be found.", _class.getSimpleName());
    }


    public EntityNotFoundException() {
        super(CODE);
        this.customMessage = String.format("Some resources could could not be found.");
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
