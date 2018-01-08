
package com.kabasakalis.springifyapi.hateoas;

        import com.fasterxml.jackson.annotation.JsonProperty;
        import com.kabasakalis.springifyapi.models.SpringifyUser;
        import org.springframework.hateoas.ResourceSupport;

public class UserResource extends ResourceSupport {

    @JsonProperty
    public long id;
    public String name;
    public String token;
    public String created_date;
    public String updated_date;

    public UserResource(SpringifyUser model){
        id = model.getId();
        name = model.getUsername();
        token = model.getJwtToken();
        created_date = model.getFormattedCreatedDate();
        updated_date = model.getFormattedUpdatedDate();
    }

}
