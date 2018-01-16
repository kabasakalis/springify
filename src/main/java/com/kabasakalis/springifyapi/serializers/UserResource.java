
package com.kabasakalis.springifyapi.serializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kabasakalis.springifyapi.domain.Role;
import com.kabasakalis.springifyapi.domain.SpringifyUser;

import java.util.stream.Collectors;

public class UserResource extends BaseResourceSupport {

    @JsonProperty
    public long id;
    public String username;
    public String email;
    public String token;
    public String roles;
    public String created_date;
    public String updated_date;

    public UserResource(SpringifyUser user){
        super(user);
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
//        roles = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(","));
//        roles = user.getRoles().isEmpty() ? "" : user.getRoles().stream().map(Role::getName).collect(Collectors.joining(","));
        token = user.getJwtToken();
        created_date = user.getFormattedCreatedDate();
        updated_date = user.getFormattedUpdatedDate();
    }

}
