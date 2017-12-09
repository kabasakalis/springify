package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("artistRepository")
public interface ArtistRepository extends JpaRepository<Artist, Long> {

}
