package com.kabasakalis.springifyapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kabasakalis.springifyapi.models.Artist;
import org.springframework.hateoas.ResourceSupport;
import java.util.Calendar;

public class ArtistResource extends ResourceSupport {

    @JsonProperty
    public long id;
    public String name;
    public long  genre_id;
    public String country;
    public Calendar created_date;
    public Calendar updated_date;

    public ArtistResource(Artist model){
        id = model.getId();
        name = model.getName();
        country = model.getCountry();
        genre_id = model.getGenre().getId();
        created_date = model.getCreatedDate();
        updated_date = model.getUpdatedDate();
    }

}
