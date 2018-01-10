
package com.kabasakalis.springifyapi.hateoas;

        import com.fasterxml.jackson.annotation.JsonProperty;
        import com.kabasakalis.springifyapi.models.BaseEntity;
        import com.kabasakalis.springifyapi.models.Role;
        import com.kabasakalis.springifyapi.models.SpringifyUser;
        import org.springframework.hateoas.ResourceSupport;

        import java.util.stream.Collectors;

public class UserResource extends BaseResourceSupport {

    @JsonProperty
    public long id;
    public String name;
    public String token;
    public String roles;
    public String created_date;
    public String updated_date;

    public UserResource(SpringifyUser user){
        super(user);
        id = user.getId();
        name = user.getUsername();
        roles = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(","));
        token = user.getJwtToken();
        created_date = user.getFormattedCreatedDate();
        updated_date = user.getFormattedUpdatedDate();
    }



}
