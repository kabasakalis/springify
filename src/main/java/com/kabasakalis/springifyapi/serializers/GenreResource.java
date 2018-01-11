package com.kabasakalis.springifyapi.serializers;

import com.kabasakalis.springifyapi.domain.Genre;

public class GenreResource extends BaseResourceSupport {

    public long id;
    public String name;
    public int artists_count;
    public String created_date;
    public String updated_date;

    public GenreResource(Genre genre){
        super(genre);
        id = genre.getId();
        name = genre.getName();
        artists_count = genre.getArtists().size();
        created_date = genre.getFormattedCreatedDate();
        updated_date = genre.getFormattedUpdatedDate();
    }

}
