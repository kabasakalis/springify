
package com.kabasakalis.springifyapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "playlists")
public class Playlist extends BaseEntity {

  private String name;

  @ManyToMany
  @JsonIgnore
  @JoinTable(name = "playlists_albums",
        joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"))
    private List<Album> albums = new ArrayList<Album>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Album> getAlbums() {
    return albums;
  }

  public void setAlbums(List<Album> albums) {
    this.albums = albums;
  }

}
