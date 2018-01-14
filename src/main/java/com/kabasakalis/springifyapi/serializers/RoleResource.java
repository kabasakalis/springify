package com.kabasakalis.springifyapi.serializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kabasakalis.springifyapi.domain.Role;

public class RoleResource extends BaseResourceSupport {

    @JsonProperty
    public long id;
    public String name;
    public String created_date;
    public String updated_date;

    public RoleResource(Role role) {
        super(role);
        id = role.getId();
        name = role.getName();
        created_date = role.getFormattedCreatedDate();
        updated_date = role.getFormattedUpdatedDate();
    }

}
