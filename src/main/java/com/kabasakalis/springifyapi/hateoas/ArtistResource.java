package com.kabasakalis.springifyapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kabasakalis.springifyapi.models.Artist;
import org.springframework.hateoas.ResourceSupport;
import java.util.Calendar;

public class ArtistResource extends ResourceSupport {

    @JsonProperty
    public long id;
    public String name;
    public String country;
    public Calendar created;
    public Object group;

    public ArtistResource(Artist model){
        id = model.getId();
        name = model.getName();
        country = model.getCountry();
        created = model.getCreatedDate();
        // group = model.getGroup();
    }

}
