package com.kabasakalis.springifyapi.repositories;

import com.kabasakalis.springifyapi.models.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Page<Artist> findAllByGenreId(Long id, Pageable pageable);

    Page<Artist> findByNameIgnoreCaseContaining(String namelike, Pageable pageable);

    Page<Artist> findByCountryIgnoreCaseContaining(String countryLike, Pageable pageable);


}
