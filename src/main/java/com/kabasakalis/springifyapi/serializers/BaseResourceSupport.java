package com.kabasakalis.springifyapi.serializers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kabasakalis.springifyapi.domain.BaseEntity;
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
