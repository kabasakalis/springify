package com.kabasakalis.springifyapi.services;

import com.kabasakalis.springifyapi.models.Artist;
// import net.vatri.ecommerce.repositories.GroupRepository;
// import net.vatri.ecommerce.repositories.OrderRepository;
import com.kabasakalis.springifyapi.repositories.ArtistRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Service
public class SpringifyService {

    @Autowired
    ArtistRepository artistRepository;

    // @Autowired
    // GroupRepository groupRepository;

    // @Autowired
    // OrderRepository orderRepository;

    @Autowired
    private SessionFactory sessionFactory;


    /* ARTIST */
    public List<Artist> getArtists(){
        return artistRepository.findAll();
    }
    public Artist getArtist(long id){
        return artistRepository.findOne(id);
    }
    public Artist saveArtist(Artist artist){
        return artistRepository.save(artist);
    }


}
