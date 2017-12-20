
package com.kabasakalis.springifyapi.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.kabasakalis.springifyapi.models.Artist;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "genres")
public class Genre  extends BaseEntity {

  private String name;

  @OneToMany(mappedBy = "genre", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private List<Artist> artists = new ArrayList<Artist>();


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Artist> getArtists() {
    return artists;
  }
  public void setArtists(List<Artist> artists) {
    this.artists = artists;
  }

}
