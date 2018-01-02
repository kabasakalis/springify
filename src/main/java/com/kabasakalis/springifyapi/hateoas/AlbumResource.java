package com.kabasakalis.springifyapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kabasakalis.springifyapi.models.Album;
import org.springframework.hateoas.ResourceSupport;

import java.util.Calendar;

public class AlbumResource extends ResourceSupport {

    @JsonProperty
    public long id;
    public String title;
    public String year;
    public String created_date;
    public String updated_date;

    public AlbumResource(Album model) {
        id = model.getId();
        title = model.getTitle();
        year = model.getYear();
        created_date = model.getFormattedCreatedDate();
        updated_date = model.getFormattedUpdatedDate();
    }

}
