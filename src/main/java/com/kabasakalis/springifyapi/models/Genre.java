
package com.kabasakalis.springifyapi.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.kabasakalis.springifyapi.models.Artist;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Calendar;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "genres")
public class Genre {

  private Long id;
  private String name;
  //relations
  private List<Artist> artists = new ArrayList<Artist>();

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_date")
  private Calendar createdDate;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_date")
  private Calendar updatedDate;

   @OneToMany(mappedBy = "genre", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    public List<Artist> getArtists() {
      return artists;
    }
  public void setArtists(List<Artist> artists) {
    this.artists = artists;
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
