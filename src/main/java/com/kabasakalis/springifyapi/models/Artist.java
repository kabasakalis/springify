package com.kabasakalis.springifyapi.models;


import javax.persistence.*;


  // create_table "artists", force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
  //   t.string   "title"
  //   t.string   "country"
  //   t.integer  "genre_id"
  //   t.datetime "created_at", null: false
  //   t.datetime "updated_at", null: false
  //   t.index ["genre_id"], name: "index_artists_on_genre_id", using: :btree
  // end


@Entity
@Table(name = "artists")
public class Artist{

    private Long id;
    private String name;
    private String country;
    private String created;//Todo - Date type...

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


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
