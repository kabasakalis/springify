
package com.kabasakalis.spingify.models;


import javax.persistence.*;


  // create_table "artists", force: :cascade, options: "ENGINE=InnoDB DEFAULT CHARSET=utf8" do |t|
  //   t.string   "title"
  //   t.string   "country"
  //   t.integer  "genre_id"
  //   t.datetime "created_at", null: false
  //   t.datetime "updated_at", null: false
  //   t.index ["genre_id"], title: "index_artists_on_genre_id", using: :btree
  // end

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
