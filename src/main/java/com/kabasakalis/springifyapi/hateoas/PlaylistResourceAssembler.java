package com.kabasakalis.springifyapi.hateoas;

import com.kabasakalis.springifyapi.models.Playlist;
import com.kabasakalis.springifyapi.controllers.PlaylistController;
import org.springframework.hateoas.SimpleIdentifiableResourceAssembler;
import org.springframework.stereotype.Component;


@Component
public class PlaylistResourceAssembler extends SimpleIdentifiableResourceAssembler<Playlist> {

	PlaylistResourceAssembler() {
		super(PlaylistController.class);
	}

}
