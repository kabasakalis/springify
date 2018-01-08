package com.kabasakalis.springifyapi.hateoas;


import com.kabasakalis.springifyapi.models.Role;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class RoleResource extends ResourceSupport {

    @JsonProperty
    public long id;
    public String name;
    public String created_date;
    public String updated_date;

    public RoleResource(Role model) {
        id = model.getId();
        name = model.getName();
        created_date = model.getFormattedCreatedDate();
        updated_date = model.getFormattedUpdatedDate();
    }

}
