
package com.kabasakalis.springifyapi.hateoas;

        import com.fasterxml.jackson.annotation.JsonProperty;
        import com.kabasakalis.springifyapi.models.User;
        import org.springframework.hateoas.ResourceSupport;

public class UserResource extends ResourceSupport {

    @JsonProperty
    public long id;
    public String name;
    public String created_date;
    public String updated_date;

    public UserResource(User model){
        id = model.getId();
        name = model.getUsername();
        created_date = model.getFormattedCreatedDate();
        updated_date = model.getFormattedUpdatedDate();
    }

}
