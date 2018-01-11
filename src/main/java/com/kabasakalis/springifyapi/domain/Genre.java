
package com.kabasakalis.springifyapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genres")
public class Genre  extends BaseEntity {

  private String name;

public Genre(){};
    @OneToMany(mappedBy = "genre", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
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
