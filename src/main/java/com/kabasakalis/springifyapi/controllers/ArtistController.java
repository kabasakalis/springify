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


  // @RequestMapping(method = RequestMethod.POST, path = "/artists", produces = MediaTypes.HAL_JSON_VALUE)
  //   ResponseEntity<Void> createArtist( @RequestBody Artist a) {
  //
  //       Artist createdArtist = springifyService.createArtist(artist);
  //       HttpHeaders httpHeaders = new HttpHeaders();
  //       httpHeaders.setLocation(linkTo(methodOn(getClass()).loadSingleUserCustomer(user, customer.getId())).toUri());
  //
  //       return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
  //   }

@RequestMapping(method = RequestMethod.POST, path = "/artists", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Resource<Artist>>  createArtist(@RequestBody @Valid Artist artist) {
      Artist createdArtist = springifyService.createArtist(artist);
       Link  artist_location = createHateoasLink(createdArtist.getId());
      return ResponseEntity
        // .created(linkTo(methodOn(ArtistController.class).findOne(createdArtist.getId())).toUri())
        .created(createHateoasLink(createdArtist.getId()))
        .body(assembler.toResource(createdArtist));

        HttpHeaders httpHeaders = new HttpHeaders();
        URI loc = new URI(artist_location.getHref());
        // System.out.println(artist_location.getHref());

        httpHeaders.setLocation(loc);
        return new ResponseEntity<>(assembler.toResource(createdArtist), httpHeaders, HttpStatus.CREATED);
        // return new ResponseEntity<>(  HttpStatus.CREATED);
    }


  // @RequestMapping(method = RequestMethod.GET, path = "/artists/{id}", produces = MediaTypes.HAL_JSON_VALUE)
  //   public ResponseEntity<Resource<Artist>>  getArtist(@PathVariable Long id) {
  //
  //     // return assembler.toResource(springifyService.getArtist(id));
  //
  //     return this.springifyService
  //       .getArtist(id)
  //       .map(artist -> {
  //         // Bookmark result = bookmarkRepository.save(new Bookmark(account,
  //         //       input.uri, input.description));
  //
  //         // URI location = ServletUriComponentsBuilder
  //         //   .fromCurrentRequest().path("/{id}")
  //         //   .buildAndExpand(result.getId()).toUri();
  //
  //         URI artist_location = createHateoasLink(id).toUri();
  //        Resource<?>  artistHAL = assembler.toResource(artist);
  //        return ResponseEntity.ok(artistHAL).build();
  //       })
  //     .orElse(ResponseEntity.notFound().build());
  //
  //
  //
  //     // return ResponseEntity.ok(assembler.toResource(springifyService.getArtist(id)));
  //
  //   }


  @RequestMapping(method = RequestMethod.GET, path = "/artists/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Resource<Artist>> getArtist(@PathVariable Long id) {
        return  this.springifyService.getArtist(id)
                .map(a -> new ResponseEntity<>(assembler.toResource(a), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                // .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }



  // @RequestMapping(method = RequestMethod.PATCH, path = "/artists/{id}", produces = MediaTypes.HAL_JSON_VALUE)
  //   public ResponseEntity<Resource<Artist>>  updateArtist(@RequestBody @Valid Artist artist, @PathVariable Long id) {
  //     return springifyService.updateArtist(id, artist);
  //     return ResponseEntity.noContent();
  //   }
  //
  // @RequestMapping(method = RequestMethod.DELETE, path = "/artists/{id}", produces = MediaTypes.HAL_JSON_VALUE)
  //   public ResponseEntity<Resource<Artist>> deleteArtist(@PathVariable Long id) {
  //     springifyService.deleteArtist(id);
  //     return ResponseEntity.noContent();
  //   }

}
