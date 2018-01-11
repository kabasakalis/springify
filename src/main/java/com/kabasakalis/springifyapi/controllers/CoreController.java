package com.kabasakalis.springifyapi.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ControllerUtils;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CoreController {


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/",
            produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResourceSupport> root(Pageable pageRequest) {
        ResourceSupport rootResource = new ResourceSupport();

        rootResource.add(
                linkTo(methodOn(this.getClass()).root(pageRequest)).withSelfRel(),
                linkTo(methodOn(GenreController.class).getAll(pageRequest)).withRel("genres"),
                linkTo(methodOn(ArtistController.class).getAll(pageRequest)).withRel("artists"),
                linkTo(methodOn(AlbumController.class).getAll(pageRequest)).withRel("albums"),
                linkTo(methodOn(PlaylistController.class).getAll(pageRequest)).withRel("playlists"));
        return ControllerUtils.toResponseEntity(HttpStatus.OK, new HttpHeaders(), rootResource);
    }

}
