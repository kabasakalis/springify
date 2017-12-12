
package com.kabasakalis.springifyapi.models;


import com.kabasakalis.springifyapi.models.Artist;
import com.kabasakalis.springifyapi.models.Playlist;

import javax.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "albums")
public class Album{

    private Integer id;
    private String title;
    private String year;

    private List<Playlist> playlists = new ArrayList<Playlist>();
    // private String country;
    // private String created;//Todo - Date type...
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private java.util.Calendar createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Calendar updatedDate;


    //relations
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    @JsonBackReference
    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

   @ManyToMany(mappedBy = "albums")
    public List<Playlist> getPlaylists() {
        return playlists;
    }

  public void setPlaylists(List<Playlist> playlists) {
    this.playlists = playlists;
  }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.title = year;
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
