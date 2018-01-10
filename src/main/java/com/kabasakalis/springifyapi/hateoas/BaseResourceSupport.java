package com.kabasakalis.springifyapi.hateoas;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kabasakalis.springifyapi.models.BaseEntity;
import org.springframework.hateoas.ResourceSupport;

public class BaseResourceSupport extends ResourceSupport {

    @JsonIgnore
    protected  BaseEntity  entity;

    public BaseResourceSupport( BaseEntity entity) {
        super();
        this.entity = entity;
    }

    public BaseEntity getEntity() {
        return entity;
    }

    public void setEntity(BaseEntity entity) {
        this.entity = entity;
    }

}
