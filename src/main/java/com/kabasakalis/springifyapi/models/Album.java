
package com.kabasakalis.springifyapi.models;


import com.kabasakalis.springifyapi.models.Artist;

import javax.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "albums")
public class Album{

    private Integer id;
    private String title;
    private String year;
    // private String country;
    // private String created;//Todo - Date type...
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private java.util.Calendar createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private java.util.Calendar updatedDate;


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

    // public String getCreated() {
    //     return created;
    // }
    //
    // public void setCreated(String created) {
    //     this.created = created;
    // }
}
