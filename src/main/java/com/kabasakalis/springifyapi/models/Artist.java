package com.kabasakalis.springifyapi.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.models.BaseEntity;
import com.kabasakalis.springifyapi.models.Genre;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Calendar;
import org.springframework.hateoas.Identifiable;

@Entity
@Table(name = "artists")
public class Artist extends BaseEntity {

    private String name;
    private String country;
    // private long genre_id;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    // @JsonBackReference
    private Genre genre;

    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    @JsonIgnore
    private List<Album> albums = new ArrayList<Album>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

   // relations
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }


    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

}
