package com.kabasakalis.springifyapi.exceptions;

import com.kabasakalis.springifyapi.domain.BaseEntity;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class AssociationNotFoundException extends BaseException {

    protected static final HttpStatus CODE = NOT_FOUND;
    private Class<? extends BaseEntity> entityClass;
    private long Id;
    private long associatedId;

    public AssociationNotFoundException(
            Class<? extends BaseEntity> _class,
            Class<? extends BaseEntity> associatedClass,
            long Id,
            long associatedId
    ) {
        super(CODE);
        this.Id = Id;
        this.associatedId = associatedId;
        this.customMessage = String.format("%2$s with id of %4$d is not associated to %1$s of id %3$d",
                _class.getSimpleName(), associatedClass.getSimpleName(), Id, associatedId);
    }

    public Class<? extends BaseEntity> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<? extends BaseEntity> entityClass) {
        this.entityClass = entityClass;
    }

    public long getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(long associatedId) {
        this.associatedId = associatedId;
    }


    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    @Override
    public String getMessage() {
        return customMessage;
    }

}
