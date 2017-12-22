

package com.kabasakalis.springifyapi.controllers;


import com.kabasakalis.springifyapi.hateoas.ArtistResource;
import com.kabasakalis.springifyapi.hateoas.AlbumResource;
import com.kabasakalis.springifyapi.hateoas.ArtistResourceAssembler;
import com.kabasakalis.springifyapi.hateoas.AlbumResourceAssembler;
import com.kabasakalis.springifyapi.models.Artist;
import com.kabasakalis.springifyapi.models.Album;
import com.kabasakalis.springifyapi.controllers.ArtistController;
import com.kabasakalis.springifyapi.models.BaseEntity;
import com.kabasakalis.springifyapi.repositories.ArtistRepository;
import com.kabasakalis.springifyapi.repositories.AlbumRepository;
import com.kabasakalis.springifyapi.repositories.AlbumRepository;

// import com.kabasakalis.springifyapi.services.SpringifyService;
import org.springframework.data.jpa.repository.JpaRepository;
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
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
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

import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;


public abstract class AbstractBaseRestController<T extends BaseEntity> {

    // private Logger logger = LoggerFactory.getLogger(RESTController.class);

    protected JpaRepository<T, Long> repository;
    protected PagedResourcesAssembler<T> pagedAssembler;

    // @Autowired
    protected SimpleIdentifiableResourceAssembler<T> assembler;

    @Autowired
    public AbstractBaseRestController(JpaRepository<T, Long> repository,
                                      SimpleIdentifiableResourceAssembler<T> assembler) {
        this.repository = repository;
        HateoasPageableHandlerMethodArgumentResolver resolver = new HateoasPageableHandlerMethodArgumentResolver();
        this.pagedAssembler = new PagedResourcesAssembler<T>(resolver, null);
        this.assembler = assembler;
    }


    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Page<T>> getAll(Pageable pageRequest) {
        PagedResources<Resource<T>> pagedResources = pagedAssembler.toResource(repository.findAll(pageRequest), assembler);
        return new ResponseEntity(pagedResources, HttpStatus.OK);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{id}",
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Resource<T>> getOne(@PathVariable Long id) {
        return Optional.ofNullable(repository.findOne(id))
                .map(o -> new ResponseEntity<>(assembler.toResource(o), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Void> addOne(@RequestBody T entityBody) {
        T entity= repository.save(entityBody);
        HttpHeaders httpHeaders = new HttpHeaders();
        URI location_link = linkTo(methodOn(ArtistController.class).getOne(entity.getId())).toUri();
        httpHeaders.setLocation(location_link);
        return new ResponseEntity(assembler.toResource(entity), httpHeaders, HttpStatus.CREATED);
    }



  @RequestMapping(
    method = RequestMethod.PATCH,
    path = "/{id}",
    produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Resource<T>> updateOne(@RequestBody T entityPatch, @PathVariable long id) {
      return  Optional.ofNullable(repository.findOne(id))
        .map( entity ->{
          entityPatch.setId(id);
          repository.save(entityPatch);
          return new ResponseEntity<>(assembler.toResource(entity), HttpStatus.OK);
        })
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Transactional
  @RequestMapping(
  method = RequestMethod.DELETE,
  path = "/{id}",
  produces = MediaTypes.HAL_JSON_VALUE)
  ResponseEntity<Resource<T>> deleteOne(@PathVariable Long id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(entity ->{
        repository.delete(entity);
        return new ResponseEntity<>(assembler.toResource(entity), HttpStatus.OK);
      })
    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }




}
