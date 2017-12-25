
package com.kabasakalis.springifyapi.models;


import com.kabasakalis.springifyapi.models.BaseEntity;
import com.kabasakalis.springifyapi.models.Artist;
import com.kabasakalis.springifyapi.models.Playlist;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "albums")
public class Album extends BaseEntity {

  private String title;
  private String year;

public Album(){};
  @ManyToMany(mappedBy = "albums")
  @JsonIgnore
  private List<Playlist> playlists = new ArrayList<Playlist>();

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

  public List<Playlist> getPlaylists() {
    return playlists;
  }

  public void setPlaylists(List<Playlist> playlists) {
    this.playlists = playlists;
  }

}
