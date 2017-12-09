package com.kabasakalis.springifyapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kabasakalis.springifyapi.models.Artist;
import org.springframework.hateoas.ResourceSupport;

public class ArtistResource extends ResourceSupport {

    @JsonProperty
    public long id;
    public String name;
    public String country;
    public String created;
    public Object group;

    public ArtistResource(Artist model){
        id = model.getId();
        name = model.getName();
        country = model.getCountry();
        created = model.getCreated();
        // group = model.getGroup();
    }

}
