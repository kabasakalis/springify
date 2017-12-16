
package com.kabasakalis.springifyapi.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.kabasakalis.springifyapi.models.Album;
// import com.fasterxml.jackson.annotation.JsonManagedReference;
// import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "playlists")
public class Playlist extends BaseEntity {

  private Long id;
  private String name;

  @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "playlist_album",
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
