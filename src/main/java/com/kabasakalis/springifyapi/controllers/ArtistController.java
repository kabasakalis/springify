package com.kabasakalis.springifyapi.controllers;

import com.kabasakalis.springifyapi.hateoas.ArtistResource;
import com.kabasakalis.springifyapi.hateoas.ArtistResourceAssembler;
import com.kabasakalis.springifyapi.models.Artist;

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
public class ArtistController {


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

    private Page<Artist> getArtists(Pageable pageRequest) {
//        int totalArtists = 50;
//        List<Account> artisistsList = IntStream.rangeClosed(1, totalArtists)
//                .boxed()
//                .map(value -> new Artist(value, value.toString()))
//                .skip(pageRequest.getOffset())
//                .limit(pageRequest.getPageSize())
//                .collect(Collectors.toList());
//        return new PageImpl(artisistsList, pageRequest, totalAccounts);


        Page<Artist> artistsList = springifyService.getArtists(pageRequest);

//        return new PageImpl(artistsList, pageRequest, 11);
        return artistsList;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/artists", produces = "application/hal+json")
    public ResponseEntity<Page<Artist>> getArtistHAL(Pageable pageRequest, ArtistResourceAssembler assembler) {
        return new ResponseEntity(pagedAssembler.toResource(getArtists(pageRequest),  assembler), HttpStatus.OK);
    }


//    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
////  @GetMapping("/{id}")
//// @RequestMapping(method = RequestMethod.GET)
////  @GetMapping( produces = MediaTypes.HAL_JSON_VALUE)
//    public Resources<Resource<Artist>> index() {
//
//        // return assembler.toResources(new ArrayList<>());
//        return assembler.toResources(springifyService.getArtists());
//    }


    @PostMapping
    public Artist create(@RequestBody @Valid Artist artist) {
        return springifyService.saveArtist(artist);
    }

//  @GetMapping("/{id}")
// @RequestMapping(method = RequestMethod.GET)
//  public ResourceSupport view(@PathVariable("id") long id){
//    Artist p = springifyService.getArtist(id);
//    ArtistResource pr = new ArtistResource(p);
//    pr.add(createHateoasLink(p.getId()));
//    return pr;
//  }

    @PatchMapping(value = "/{id}")
    public Artist edit(@PathVariable("id") long id, @RequestBody @Valid Artist artist) {
        Artist updatedArtist = springifyService.getArtist(id);
        if (updatedArtist == null) {
            return null;
        }

        updatedArtist.setName(artist.getName());
        updatedArtist.setCountry(artist.getCountry());
        return springifyService.saveArtist(updatedArtist);
    }


    // Todo: add delete method

}
