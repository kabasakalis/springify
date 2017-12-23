package com.kabasakalis.springifyapi.hateoas;

import com.kabasakalis.springifyapi.models.Playlist;
import com.kabasakalis.springifyapi.controllers.PlaylistController;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Component;

import org.springframework.hateoas.Resources;
import org.springframework.hateoas.Resource;


@Component
public class PlaylistResourceAssembler extends SimpleIdentifiableResourceAssembler<Playlist> {

	PlaylistResourceAssembler() {
		super(PlaylistController.class);
	}

}
