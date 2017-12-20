package com.kabasakalis.springifyapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kabasakalis.springifyapi.models.Genre;
import org.springframework.hateoas.ResourceSupport;
import java.util.Calendar;

public class GenreResource extends ResourceSupport {

    @JsonProperty
    public long id;
    public String name;
    public Calendar created_date;
    public Calendar updated_date;

    public GenreResource(Genre model){
        id = model.getId();
        name = model.getName();
        created_date = model.getCreatedDate();
        updated_date = model.getUpdatedDate();
    }

}
