package com.kabasakalis.springifyapi.services;

import com.kabasakalis.springifyapi.models.Artist;
import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.models.Genre;
import com.kabasakalis.springifyapi.repositories.ArtistRepository;
import com.kabasakalis.springifyapi.repositories.AlbumRepository;
import com.kabasakalis.springifyapi.repositories.GenreRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Service
public class SpringifyService {

  @Autowired
  ArtistRepository artistRepository;

  @Autowired
  AlbumRepository albumRepository;

  @Autowired
  GenreRepository genreRepository;


  @Autowired
  private SessionFactory sessionFactory;


  /* ARTIST */
  public List<Artist> getArtists(){
    return artistRepository.findAll();
  }

  public Page<Artist> getPagedArtists(Pageable pageable){
    return artistRepository.findAll( pageable);
  }

  // public Optional<Artist> getArtist(long id){
  //   return artistRepository.findOne(id);
  // }

  public Optional<Artist> getArtist(long id){
    return artistRepository.findById(id);
  }



  public Artist createArtist(Artist artist){
    return artistRepository.save(artist);
  }

  // public Artist createArtist(Artist artist){
  //   return artistRepository.addArtist(artist.getGenre().getId(), artist.getName(), artist.getCountry());
  // }


  // public Artist updateArtist(Long id, Artist artist){
  //
  //     Artist updatedArtist = getArtist(id);
  //     if (updatedArtist == null) {
  //       return null;
  //     }
  //     updatedArtist.setName(artist.getName());
  //     updatedArtist.setCountry(artist.getCountry());
  //     updatedArtist.setGenre(artist.getGenre());
  //     return artistRepository.save(artist);
  // }


  public void deleteArtist(long id){
    Artist artist =  artistRepository.findOne(id);
    artistRepository.delete(artist);
  }


  /* ALBUM */
  public List<Album> getAlbums(){
    return albumRepository.findAll();
  }
  public Album getAlbum(long id){
    return albumRepository.findOne(id);
  }
  public Album saveAlbum(Album album){
    return albumRepository.save(album);
  }


  /* Genres */
  public List<Genre> getGenres(){
    return genreRepository.findAll();
  }
  public Genre getGenre(long id){
    return genreRepository.findOne(id);
  }
  public Genre saveGenre(Genre genre){
    return genreRepository.save(genre);
  }





}
