package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.ArtistResource;
import com.kabasakalis.springifyapi.hateoas.ArtistResourceAssembler;
import com.kabasakalis.springifyapi.models.Artist;
import com.kabasakalis.springifyapi.controllers.ArtistController;

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


import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RepositoryRestController
// @RequestMapping("/api/artistss")
//@RequestMapping("/artists")
public class ArtistController extends CoreController {


  private PagedResourcesAssembler<Artist> pagedAssembler;

  @Autowired
  private SpringifyService springifyService;

  @Autowired
  private ArtistResourceAssembler assembler;

  @Autowired
  private SessionFactory sessionFactory;


  @Autowired
  public ArtistController(PagedResourcesAssembler<Artist> pagedAssembler) {
    this.pagedAssembler = pagedAssembler;
  }
  // @Autowired Validator artistValidator;

  // @InitBinder
  // protected void initBinder(WebDataBinder binder){
  //     binder.addValidators(artistValidator);
  // }


  @RequestMapping(method = RequestMethod.GET, path = "/artists", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Page<Artist>> getArtistsHAL(Pageable pageRequest, ArtistResourceAssembler assembler) {
      return new ResponseEntity(pagedAssembler.toResource(springifyService.getPagedArtists(pageRequest),  assembler), HttpStatus.OK);
    }

  @RequestMapping(method = RequestMethod.GET, path = "/artists/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Resource<Artist>>  getArtist(@PathVariable Long id) {
      // return assembler.toResource(springifyService.getArtist(id));
      return ResponseEntity.ok(assembler.toResource(springifyService.getArtist(id)));

    }

  @RequestMapping(method = RequestMethod.POST, path = "/artists", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Resource<Artist>>  createArtist(@RequestBody @Valid Artist artist) {
      Artist createdArtist = springifyService.createArtist(artist);
		return ResponseEntity
			// .created(linkTo(methodOn(ArtistController.class).findOne(createdArtist.getId())).toUri())
			.created(createHateoasLink(createdArtist.getId()))
			.body(assembler.toResource(createdArtist));
	}


  @RequestMapping(method = RequestMethod.PATCH, path = "/artists/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Resource<Artist>>  updateArtist(@RequestBody @Valid Artist artist, @PathVariable Long id) {
      return springifyService.updateArtist(id, artist);
      return ResponseEntity.noContent();
    }

  @RequestMapping(method = RequestMethod.DELETE, path = "/artists/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Resource<Artist>> deleteArtist(@PathVariable Long id) {
      springifyService.deleteArtist(id);
      return ResponseEntity.noContent();
    }

}
