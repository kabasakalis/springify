package com.kabasakalis.springifyapi.serializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kabasakalis.springifyapi.domain.Artist;
import com.kabasakalis.springifyapi.domain.Genre;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class ArtistResource extends BaseResourceSupport {

    @JsonProperty
    public long id;
    public String name;
    public String  genre;
    public String country;
    public  int  albums_count;
    public String created_date;
    public String updated_date;

    public ArtistResource(Artist artist){
        super(artist);
        id = artist.getId();
        name = artist.getName();
        if (artist.getGenre() != null) {genre =   artist.getGenre().getName();};
        country = artist.getCountry();
        albums_count = artist.getAlbums().size();
        created_date = artist.getFormattedCreatedDate();
        updated_date = artist.getFormattedUpdatedDate();
    }


}
