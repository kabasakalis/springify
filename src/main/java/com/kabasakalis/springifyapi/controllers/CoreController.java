package com.kabasakalis.springifyapi.controllers;

import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.ResourceSupport;

@RestController
public class CoreController {
    protected Link createHateoasLink(long id){
        Link link = linkTo(getClass()).slash(id).withSelfRel();
        return link;
    }

	@GetMapping(value = "/home", produces = MediaTypes.HAL_JSON_VALUE)
	public ResourceSupport root() {

		ResourceSupport rootResource = new ResourceSupport();

		rootResource.add(
			linkTo(methodOn(ArtistController.class).root()).withSelfRel(),
			linkTo(methodOn(AlbumController.class).index()).withRel("albumsss"));
		return rootResource;
	}



}
