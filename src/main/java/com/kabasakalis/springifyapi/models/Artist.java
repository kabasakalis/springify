package com.kabasakalis.springifyapi.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.models.Genre;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Calendar;
import org.springframework.hateoas.Identifiable;

@Entity
@Table(name = "artists")
public class Artist implements Identifiable<Long> {

    private Long id;
    private String name;
    private String country;
    // private String created;//Todo - Date type...

    private Genre genre;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private java.util.Calendar createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private java.util.Calendar updatedDate;


    //relations
    private List<Album> albums = new ArrayList<Album>();

    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    public List<Album> getAlbums() {
        return albums;
    }
    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    @ManyToOne
    @JoinColumn(name = "genre_id")
    @JsonBackReference
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


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

  public Calendar getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Calendar created) {
    this.createdDate = created;
  }
  public Calendar getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Calendar updated) {
    this.updatedDate = updated;
  }

}
