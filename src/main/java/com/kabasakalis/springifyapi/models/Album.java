
package com.kabasakalis.springifyapi.models;


import com.kabasakalis.springifyapi.models.Artist;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
  // create_table "albums", force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
  //   t.string   "title"
  //   t.integer  "year"
  //   t.integer  "artist_id"
  //   t.datetime "created_at", null: false
  //   t.datetime "updated_at", null: false
  //   t.index ["artist_id"], title: "index_albums_on_artist_id", using: :btree
  // end

@Entity
@Table(name = "albums")
public class Album{

    private Integer id;
    private String title;
    private String year;
    // private String country;
    private String created;//Todo - Date type...


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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
