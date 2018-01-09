
package com.kabasakalis.springifyapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "playlists")
public class Playlist extends BaseEntity {

  private String name;

  @ManyToMany
  @JsonIgnore
  @JoinTable(name = "playlists_albums",
        joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"))
    private Set<Album> albums = new HashSet<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Album> getAlbums() {
    return albums;
  }

  public void setAlbums(Set<Album> albums) {
    this.albums = albums;
  }

}
