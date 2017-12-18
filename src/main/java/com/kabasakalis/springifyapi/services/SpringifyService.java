package com.kabasakalis.springifyapi.services;

import com.kabasakalis.springifyapi.models.Artist;
import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.repositories.ArtistRepository;
import com.kabasakalis.springifyapi.repositories.AlbumRepository;
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

@Service
public class SpringifyService {

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    AlbumRepository albumRepository;

    // @Autowired
    // GroupRepository groupRepository;

    // @Autowired
    // OrderRepository orderRepository;

    @Autowired
    private SessionFactory sessionFactory;


    /* ARTIST */
    public Page<Artist> getArtists(Pageable pageable){
        return artistRepository.findAll( pageable);
    }
    public Artist getArtist(long id){
        return artistRepository.findOne(id);
    }
    public Artist saveArtist(Artist artist){
        return artistRepository.save(artist);
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





}
