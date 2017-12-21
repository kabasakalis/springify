package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.GenreResource;
import com.kabasakalis.springifyapi.hateoas.GenreResourceAssembler;
import com.kabasakalis.springifyapi.hateoas.ArtistResource;
import com.kabasakalis.springifyapi.hateoas.ArtistResourceAssembler;
import com.kabasakalis.springifyapi.models.Genre;
import com.kabasakalis.springifyapi.models.Artist;
import com.kabasakalis.springifyapi.controllers.ArtistController;
import com.kabasakalis.springifyapi.repositories.GenreRepository;
import com.kabasakalis.springifyapi.repositories.ArtistRepository;

import com.kabasakalis.springifyapi.services.SpringifyService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

import org.springframework.hateoas.Link;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.net.URI;
import java.util.Optional;



import com.kabasakalis.springifyapi.repositories.GenreRepository;

@RepositoryRestController
public class GenreController extends CoreController{

  @Autowired
  private SpringifyService springifyService;

  @Autowired
  private  GenreResourceAssembler genreAssembler;

  @Autowired
  private  ArtistResourceAssembler artistAssembler;


  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  GenreRepository genreRepository;

  @Autowired
  ArtistRepository artistRepository;

  private PagedResourcesAssembler<Genre> pagedGenreAssembler;
  private PagedResourcesAssembler<Artist> artistPagedAssembler;

  @Autowired
  public GenreController(PagedResourcesAssembler<Genre> pagedGenreAssembler, PagedResourcesAssembler<Artist> artistPagedAssembler) {
    this.pagedGenreAssembler = pagedGenreAssembler;
    this.artistPagedAssembler = artistPagedAssembler;
  }


  @RequestMapping(
  method = RequestMethod.GET,
  path = "/genres",  produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Page<Genre>> getGenres(
        Pageable pageRequest,
        GenreResourceAssembler genreAssembler) {
            PagedResources<Resource<Genre>> pagedGenres = pagedGenreAssembler.toResource(genreRepository.findAll( pageRequest), genreAssembler);
            return new ResponseEntity(pagedGenres, HttpStatus.OK);
        }


  @RequestMapping(
  method = RequestMethod.GET,
  path = "/genres/{id}",
  produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Resource<Genre>> getGenre(@PathVariable Long id) {
      return  this.genreRepository.findById(id)
        .map(g -> new ResponseEntity<>(genreAssembler.toResource(g), HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


  @RequestMapping(
  method = RequestMethod.GET,
  path = "/genres/{id}/artists",
  produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Page<Artist>> getGenreArtists(
        Pageable pageRequest,
        ArtistResourceAssembler artistAssembler,
        @PathVariable Long id) {
            PagedResources<Resource<Artist>>  pagedGenreArtists = artistPagedAssembler.toResource(artistRepository.findAllByGenreId(id,pageRequest), artistAssembler);
            return new ResponseEntity( pagedGenreArtists, HttpStatus.OK);
        }

}
