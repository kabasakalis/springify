
package com.kabasakalis.springifyapi.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "albums")
public class Album extends BaseEntity {

  private String title;
  private String year;

public Album(){};
  @ManyToMany(mappedBy = "albums")
  @JsonIgnore
  private Set<Playlist> playlists = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "artist_id")
  @JsonBackReference
  private Artist artist;

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
    this.year = year;
  }

  public Artist getArtist() {
    return artist;
  }

  public void setArtist(Artist artist) {
    this.artist = artist;
  }

  public Set<Playlist> getPlaylists() {
    return playlists;
  }

  public void setPlaylists(Set<Playlist> playlists) {
    this.playlists = playlists;
  }

}
