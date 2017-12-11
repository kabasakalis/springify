
package com.kabasakalis.springifyapi.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.kabasakalis.springifyapi.models.Album;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "playlists")
public class Playlist {

  private Long id;
  private String name;
  //relations
  private List<Album> albums = new ArrayList<Album>();

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date")
  private java.util.Calendar createdDate;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_date")
  private java.util.Calendar updatedDate;


  @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "playlist_album",
        joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"))
    public List<Album> getAlbums() {
      return albums;
    }

  public void setAlbums(List<Album> albums) {
    this.albums = albums;
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

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String created) {
    this.createdDate = created;
  }
  public String getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(String updated) {
    this.updatedDate = updated;
  }

}
